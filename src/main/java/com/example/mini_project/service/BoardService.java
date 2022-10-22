package com.example.mini_project.service;


import com.example.mini_project.dto.requestDto.BoardRequestDto;
import com.example.mini_project.dto.responseDto.BoardResponseDto;
import com.example.mini_project.dto.responseDto.CommentResponseDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Comment;
import com.example.mini_project.entity.Member;
import com.example.mini_project.repository.BoardRepository;
import com.example.mini_project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    public ResponseDto<?> getAllPost() {
        return ResponseDto.success(boardRepository.findAllByOrderByCreatedAtDesc());
    }


    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long boardId, Member member) {
        Board board = isPresentBoard(boardId);
        if (board == null) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID입니다.");
        }

        List<Comment> commentList = commentRepository.findAllByBoardOrderByCreatedAtDesc(board);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .image(comment.getMember().getImage())
                            .writer(comment.getMember().getName())
                            .build()
            );
        }

        return ResponseDto.success(
                BoardResponseDto.builder()
                        .name(member.getName())    // 로그인 한 사람
                        .title(board.getTitle())
                        .image(board.getImage())
                        .writer(board.getMember().getName())  // 게시물 작성자
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt())
                        .commentResponseDtoList(commentResponseDtoList)
                        .build()
        );
    }

    public Board isPresentBoard(Long boardId) {

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        return optionalBoard.orElse(null);
    }


    @Transactional
    public ResponseDto<?> createBoard(BoardRequestDto boardRequestDto, Member member) {

        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .image(boardRequestDto.getImage())
                .member(member)
                .content(boardRequestDto.getContent())
                .category(boardRequestDto.getCatagory())
                .build();

        boardRepository.save(board);
        return ResponseDto.success("게시물 생성 성공");
    }

    public ResponseDto<?> updateBoard(Long boardId, BoardRequestDto boardRequestDto, Member member) {

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new RuntimeException("게시물을 찾을 수 없습니다.")
        );

        if (board.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정 가능합니다.");
        }

        board.update(boardRequestDto);
        return ResponseDto.success("게시글 수정 완료");
    }

    public ResponseDto<?> deletePost(Long boardId, Member member) {

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new RuntimeException("게시글을 찾을 수 없습니다")
        );

        if(board.validateMember(member)){
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제 가능합니다.");
        }

        boardRepository.delete(board);
        return ResponseDto.success("게시글 삭제 완료");
    }
}
