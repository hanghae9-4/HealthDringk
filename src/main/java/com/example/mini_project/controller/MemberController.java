package com.example.mini_project.controller;

import com.example.mini_project.dto.requestDto.*;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.MemberDetailsImpl;
import com.example.mini_project.security.jwt.JwtUtil;
import com.example.mini_project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.signup(memberRequestDto);
    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody MemberRequestDto memberRequestDto, HttpServletResponse httpServletResponse) {
        return memberService.login(memberRequestDto, httpServletResponse);
    }

//    @PostMapping("/logout")
//    public ResponseDto<?> logout(HttpServletRequest httpServletRequest) {
//        return memberService.logout(httpServletRequest);
//    }

    @PostMapping("/check-id")
    public ResponseDto<?> checkId(@RequestBody CheckIdRequestDto checkIdRequestDto) {
        return memberService.checkId(checkIdRequestDto);
    }

    @GetMapping("/renew")
    public ResponseDto<?> issuedToken(@AuthenticationPrincipal MemberDetailsImpl userDetails, HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getMember().getName(),"Access"));
        return ResponseDto.success("토큰 갱신 완료");
    }

    @GetMapping ("/mypage")
    public ResponseDto<?> mypage(@AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl){
        return memberService.getMypage(memberDetailsImpl);
    }

    @PutMapping ("/mypage/image")
    public ResponseDto<?> changeImage(@RequestBody ChangeMemberInfoRequestDto changeMemberInfoRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl){
        return memberService.changeImage(changeMemberInfoRequestDto, memberDetailsImpl);
    }

    @PutMapping ("/mypage/password")
    public ResponseDto<?> changePassword(@RequestBody ChangeMemberInfoRequestDto changeMemberInfoRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl){
        return memberService.changePassword(changeMemberInfoRequestDto, memberDetailsImpl);
    }


}
