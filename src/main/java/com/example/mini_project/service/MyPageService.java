package com.example.mini_project.service;

import com.example.mini_project.dto.requestDto.ChangeMemberInfoRequestDto;
import com.example.mini_project.dto.responseDto.BoardListResponseDto;
import com.example.mini_project.dto.responseDto.MypageResponseDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Member;
import com.example.mini_project.exception.customExceptions.NotFoundImageException;
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

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3UploadService s3UploadService;

    public ResponseDto<?> getMypage(Member member) {

        List<Board> boardList = boardRepository.findAllByMemberOrderByCreatedAtDesc(member);
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
    public ResponseDto<?> changeImage(ChangeMemberInfoRequestDto changeMemberInfoRequestDto, Member member) throws IOException {

        if(changeMemberInfoRequestDto.getImage() == null) throw new NotFoundImageException();

        String imageUrl = s3UploadService.upload(changeMemberInfoRequestDto.getImage(), "member");

        member.updateImage(imageUrl);
        memberRepository.save(member);

        return ResponseDto.success("이미지 변경 성공");
    }

    @Transactional
    public ResponseDto<?> changePassword(ChangeMemberInfoRequestDto changeMemberInfoRequestDto, Member member) {

        if(!passwordEncoder.matches(changeMemberInfoRequestDto.getCurrentPassword(), member.getPassword())){
            throw new NotMatchedPasswordException();
        }

        member.updatePassword(passwordEncoder.encode(changeMemberInfoRequestDto.getModifiedPassword()));
        memberRepository.save(member);

        return ResponseDto.success("비밀번호 변경 성공");
    }

}
