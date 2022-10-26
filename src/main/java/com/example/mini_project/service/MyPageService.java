package com.example.mini_project.service;

import com.example.mini_project.dto.requestDto.ChangeMemberInfoRequestDto;
import com.example.mini_project.dto.responseDto.BoardListResponseDto;
import com.example.mini_project.dto.responseDto.MypageResponseDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Member;
import com.example.mini_project.exception.customExceptions.NotFoundImageException;
import com.example.mini_project.exception.customExceptions.NotFoundMemberException;
import com.example.mini_project.exception.customExceptions.NotMatchedPasswordException;
import com.example.mini_project.repository.BoardRepository;
import com.example.mini_project.repository.HeartRepository;
import com.example.mini_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3UploadService s3UploadService;

    public ResponseDto<?> getMypage(MemberDetailsImpl memberDetailsImpl) {

        Member member = isPresentMember(memberDetailsImpl);
        if (null == member){
            throw new NotFoundMemberException();
        }

        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardListResponseDto> boardListResponseDtoList = new ArrayList<>();
        for(Board board : boardList){
            boardListResponseDtoList.add(
                    BoardListResponseDto.builder()
                            .id(board.getId())
                            .category(board.getCategory())
                            .image(board.getImage())
                            .title(board.getTitle())
                            .content(board.getContent())
                            .writer(board.getMember().getName())
                            .heartNum(heartRepository.countByBoard(board))
                            .createdAt(board.getCreatedAt())
                            .build());
        }
        return ResponseDto.success(
                MypageResponseDto.builder()
                        .name(member.getName())
                        .image(member.getImage())
                        .boardListResponseDtoList(boardListResponseDtoList)
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> changeImage(ChangeMemberInfoRequestDto changeMemberInfoRequestDto, MemberDetailsImpl memberDetailsImpl) throws IOException {

        Member member = memberRepository.findByName(memberDetailsImpl.getUsername()).orElseThrow(
                NotFoundMemberException::new
        );

        if(changeMemberInfoRequestDto.getImage() == null) throw new NotFoundImageException();

        String imageUrl = s3UploadService.upload(changeMemberInfoRequestDto.getImage(), "member");

        member.updateImage(imageUrl);
        return ResponseDto.success("이미지 변경 성공");
    }

    @Transactional
    public ResponseDto<?> changePassword(ChangeMemberInfoRequestDto changeMemberInfoRequestDto, MemberDetailsImpl memberDetailsImpl) {

        Member member = memberRepository.findByName(memberDetailsImpl.getUsername()).orElseThrow(
                NotFoundMemberException::new
        );

        if(!passwordEncoder.matches(changeMemberInfoRequestDto.getCurrentPassword(), member.getPassword())){
            throw new NotMatchedPasswordException();
        }

        changeMemberInfoRequestDto.setModifiedPassword(passwordEncoder.encode(changeMemberInfoRequestDto.getModifiedPassword()));
        member.updatePassword(changeMemberInfoRequestDto);

        return ResponseDto.success("비밀번호 변경 성공");
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(MemberDetailsImpl memberDetailsImpl){
        Optional<Member> member = memberRepository.findById(memberDetailsImpl.getMember().getId());
        return member.orElse(null);
    }

}
