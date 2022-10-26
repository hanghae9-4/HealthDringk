package com.example.mini_project.controller;

import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.service.MemberDetailsImpl;
import com.example.mini_project.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/{boardId}")
    public ResponseDto<?> heart(@PathVariable Long boardId, @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl){
        return heartService.heart(boardId, memberDetailsImpl);
    }
}
