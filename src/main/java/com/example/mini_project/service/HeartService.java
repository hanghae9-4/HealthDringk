package com.example.mini_project.service;

import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Heart;
import com.example.mini_project.entity.Member;
import com.example.mini_project.exception.customExceptions.NotFoundBoardException;
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
    public ResponseDto<?> heart(Long boardId, MemberDetailsImpl memberDetailsImpl) {

        Board board = isPresentBoard(boardId);
        if(null == board){
            throw new NotFoundBoardException();
        }

        Member member = memberDetailsImpl.getMember();
        Optional<Heart> heart = heartRepository.findHeartByMemberAndBoardId(member, boardId);

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
