package com.example.mini_project.controller;

import com.example.mini_project.dto.requestDto.BoardRequestDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.UserDetailsImpl;
import com.example.mini_project.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private BoardService boardService;

    // 전체 게시물 조회
    @GetMapping("/board/")
    public ResponseDto<?> getAllPost() {
        return boardService.getAllPost();
    }

    // 게시글 상세 조회
    @GetMapping("/board/{boardId}")
    public ResponseDto<?> getPost(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl memberDetails) {
        return boardService.getPost(boardId, memberDetails.getMember());
    }

    // 게시물 등록
    @PostMapping("/board/")
    public ResponseDto<?> createBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl memberDetails) {
        return boardService.createBoard(boardRequestDto, memberDetails.getMember());
    }

    @PutMapping("/board/{boardId}")
    public ResponseDto<?> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl memberDetails){
        return boardService.updateBoard(boardId, boardRequestDto, memberDetails.getMember());
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseDto<?> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl memberDetails){
        return boardService.deletePost(boardId, memberDetails.getMember());
    }

}
