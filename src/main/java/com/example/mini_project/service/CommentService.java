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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardService boardService;

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto commentRequestDto, Long boardId, Member member) {

        Board board = boardRepository.findById(boardId).orElseThrow(
                NotFoundBoardException::new
        );

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
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

        if (!boardRepository.existsById(comment.getBoard().getId())) {
            throw new NotFoundBoardException();
        }

        if (comment.getMember().getName().equals(member.getName())) {
            throw new NotValidWriterException();
        }

        comment.update(commentRequestDto);
        commentRepository.save(comment);

        return ResponseDto.success("댓글 수정 완료");
    }


    @Transactional
    public ResponseDto<?> deleteComment(Long commentId, Member member) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                NotFoundCommentException::new
        );

        if (!boardRepository.existsById(comment.getBoard().getId())) {
            throw new NotFoundBoardException();
        }

        if (comment.getMember().getName().equals(member.getName())) {
            throw new NotValidWriterException();
        }

        commentRepository.delete(comment);
        return ResponseDto.success("댓글 삭제 완료");
    }
}
