package com.example.mini_project.controller;

import com.example.mini_project.dto.requestDto.*;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.UserDetailsImpl;
import com.example.mini_project.security.jwt.JwtUtil;
import com.example.mini_project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
    public ResponseDto<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        return memberService.login(loginRequestDto, httpServletResponse);
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
    public ResponseDto<?> issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getMember().getName(),"Access"));
        return ResponseDto.success("토큰 갱신 완료");
    }

    @GetMapping ("/mypage")
    public ResponseDto<?> mypage(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return memberService.getMypage(userDetailsImpl);
    }

//    @PutMapping ("/mypage/image")
//    public ResponseDto<?> changeImage(@RequestBody ChangeImageRequestDto changeImageRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
//        return memberService.changeImage(changeImageRequestDto, userDetailsImpl);
//    }
//
//    @PutMapping ("/mypage/password")
//    public ResponseDto<?> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
//        return memberService.changePassword(changePasswordRequestDto, userDetailsImpl);
//    }


}
