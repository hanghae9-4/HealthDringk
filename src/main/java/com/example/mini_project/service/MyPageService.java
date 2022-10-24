package com.example.mini_project.service;

import com.example.mini_project.dto.requestDto.ChangeMemberInfoRequestDto;
import com.example.mini_project.dto.responseDto.BoardListResponseDto;
import com.example.mini_project.dto.responseDto.MypageResponseDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Member;
import com.example.mini_project.repository.BoardRepository;
import com.example.mini_project.repository.HeartRepository;
import com.example.mini_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ResponseDto<?> getMypage(MemberDetailsImpl memberDetailsImpl) {

        Member member = isPresentMember(memberDetailsImpl);
        if (null == member){
            return ResponseDto.fail("NOT_FOUND","존재하지 않는 회원입니다.");
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

    @Transactional(readOnly = true)
    public Member isPresentMember(MemberDetailsImpl memberDetailsImpl){
        Optional<Member> member = memberRepository.findById(memberDetailsImpl.getMember().getId());
        return member.orElse(null);
    }

    @Transactional
    public ResponseDto<?> changeImage(ChangeMemberInfoRequestDto changeMemberInfoRequestDto, MemberDetailsImpl memberDetailsImpl) {

        Member member = memberRepository.findByName(memberDetailsImpl.getMember().getName()).orElseThrow(
                () -> new RuntimeException("Not found account")
        );

        member.updateInfo(changeMemberInfoRequestDto);
        return ResponseDto.success("이미지 변경 성공");
    }

    @Transactional
    public ResponseDto<?> changePassword(ChangeMemberInfoRequestDto changeMemberInfoRequestDto, MemberDetailsImpl memberDetailsImpl) {

        Member member = memberRepository.findByName(memberDetailsImpl.getMember().getName()).orElseThrow(
                () -> new RuntimeException("Not found account")
        );

        if(!passwordEncoder.matches(changeMemberInfoRequestDto.getCurrentPassword(), member.getPassword())){
            return ResponseDto.fail("NOT_MATCH_PASSWORD", "비밀번호가 일치하지 않습니다.");
        }

        changeMemberInfoRequestDto.setModifiedPassword(passwordEncoder.encode(changeMemberInfoRequestDto.getModifiedPassword()));
        member.updateInfo(changeMemberInfoRequestDto);

        return ResponseDto.success("비밀번호 변경 성공");
    }

}
