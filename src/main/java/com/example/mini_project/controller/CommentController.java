package com.example.mini_project.controller;

import com.example.mini_project.dto.requestDto.CommentRequestDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.MemberDetailsImpl;
import com.example.mini_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    // 댓글 생성
    @PostMapping("/comment/{boardId}")
    public ResponseDto<?> createComment(@PathVariable Long boardId, @RequestBody CommentRequestDto commentRequestDto,@AuthenticationPrincipal MemberDetailsImpl memberDetails){

        return commentService.createComment(commentRequestDto, boardId, memberDetails.getMember());
    }

    // 댓글 수정
    @PutMapping("/comment/{commentId}")
    public ResponseDto<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return commentService.updateComment(commentId, commentRequestDto, memberDetails.getMember());
    }

    // 댓글 삭제
    @DeleteMapping("comment/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return commentService.deleteComment(commentId, memberDetails.getMember());
    }
}
