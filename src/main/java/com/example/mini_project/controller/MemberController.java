package com.example.mini_project.controller;

import com.example.mini_project.dto.requestDto.CheckIdRequestDto;
import com.example.mini_project.dto.requestDto.MemberRequestDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        return memberService.signup(memberRequestDto);
    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody MemberRequestDto memberRequestDto, HttpServletResponse response) {
        return memberService.login(memberRequestDto, response);
    }

    @PostMapping("/reissue")
    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return memberService.reissue(request, response);
    }

    @PostMapping("/check-id")
    public ResponseDto<?> checkId(@RequestBody CheckIdRequestDto checkIdRequestDto) {
        return memberService.checkId(checkIdRequestDto);
    }

}
