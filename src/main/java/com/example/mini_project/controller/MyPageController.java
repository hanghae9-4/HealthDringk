package com.example.mini_project.controller;

import com.example.mini_project.dto.requestDto.ChangeMemberInfoRequestDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.service.MemberDetailsImpl;
import com.example.mini_project.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/")
    public ResponseDto<?> mypage(@AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl){
        return myPageService.getMypage(memberDetailsImpl);
    }

    @PutMapping("/image")
    public ResponseDto<?> changeImage(@RequestBody ChangeMemberInfoRequestDto changeMemberInfoRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl){
        return myPageService.changeImage(changeMemberInfoRequestDto, memberDetailsImpl);
    }

    @PutMapping ("/password")
    public ResponseDto<?> changePassword(@RequestBody ChangeMemberInfoRequestDto changeMemberInfoRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl){
        return myPageService.changePassword(changeMemberInfoRequestDto, memberDetailsImpl);
    }

}
