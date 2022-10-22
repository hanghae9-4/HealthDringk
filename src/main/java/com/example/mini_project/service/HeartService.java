package com.example.mini_project.service;

import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Heart;
import com.example.mini_project.entity.Member;
import com.example.mini_project.entity.UserDetailsImpl;
import com.example.mini_project.repository.BoardRepository;
import com.example.mini_project.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ResponseDto<?> heart(Long boardId, UserDetailsImpl userDetailsImpl) {

        //게시물이 있는지 없는지
        Board board = isPresentBoard(boardId);
        if(null == board){
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시물 입니다.");
        }

        //자신이 좋아요를 했는지 안했는지
        Member member = userDetailsImpl.getMember();
        Optional<Heart> heart = heartRepository.findHeartByMemberAndBoardId(member, boardId);
        //안했으면 Heart 생성
        if(heart.isEmpty()) {
            Heart newHeart = Heart.builder()
                    .member(member)
                    .board(board)
                    .build();
            heartRepository.save(newHeart);
            return ResponseDto.success("좋아요!");
        }else {
            heartRepository.deleteHeartByMemberAndBoard(member, board);
            return ResponseDto.success("좋아요 취소!");
        }

    }

    @Transactional(readOnly = true)
    public Board isPresentBoard(Long bordId){
        Optional<Board> board = boardRepository.findById(bordId);
        return board.orElse(null);
    }
}
