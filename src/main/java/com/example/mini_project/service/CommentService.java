package com.example.mini_project.service;

import com.example.mini_project.dto.requestDto.CommentRequestDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Comment;
import com.example.mini_project.entity.Member;
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

//        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException(""))

        Board board = boardService.isPresentBoard(boardId);
        if (board == null) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글입니다.");
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
                () -> new RuntimeException("댓글을 찾을 수 없습니다.")
        );

        Board board = boardService.isPresentBoard(comment.getBoard().getId());
        if (board == null) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }


        if (comment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
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
                () -> new RuntimeException("댓글을 찾을 수 없습니다.")
        );
        Board board = boardService.isPresentBoard(comment.getBoard().getId());
        if (board == null) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (comment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        commentRepository.delete(comment);
        return ResponseDto.success("댓글 삭제 완료");
    }
}
