package com.example.mini_project.service;

import com.example.mini_project.dto.requestDto.CommentRequestDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Comment;
import com.example.mini_project.entity.Member;
import com.example.mini_project.exception.customExceptions.NotFoundBoardException;
import com.example.mini_project.exception.customExceptions.NotFoundCommentException;
import com.example.mini_project.exception.customExceptions.NotValidWriterException;
import com.example.mini_project.repository.BoardRepository;
import com.example.mini_project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardService boardService;

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto commentRequestDto, Long boardId, Member member) {

        Board board = boardService.isPresentBoard(boardId);
        if (board == null) {
            throw new NotFoundBoardException();
        }

        Comment comment = Comment.builder()
                .content(commentRequestDto.getComment())
                .board(board)
                .member(member)
                .build();
        commentRepository.save(comment);
        return ResponseDto.success(
                "댓글 작성 성공."
        );
    }


    @Transactional
    public ResponseDto<?> updateComment(Long commentId, CommentRequestDto commentRequestDto, Member member) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                NotFoundCommentException::new
        );

        Board board = boardService.isPresentBoard(comment.getBoard().getId());
        if (board == null) {
            throw new NotFoundBoardException();
        }

        if (comment.validateMember(member)) {
            throw new NotValidWriterException();
        }

        comment.update(commentRequestDto);
        return ResponseDto.success("댓글 수정 완료");
    }

    private Comment isPresentComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElse(null);
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long commentId, Member member) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                NotFoundCommentException::new
        );

        Board board = boardService.isPresentBoard(comment.getBoard().getId());
        if (board == null) {
            throw new NotFoundBoardException();
        }

        if (comment.validateMember(member)) {
            throw new NotValidWriterException();
        }

        commentRepository.delete(comment);
        return ResponseDto.success("댓글 삭제 완료");
    }
}
