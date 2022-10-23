package com.example.mini_project.service;

import com.example.mini_project.dto.requestDto.*;
import com.example.mini_project.dto.responseDto.BoardListResponseDto;
import com.example.mini_project.dto.responseDto.MypageResponseDto;
import com.example.mini_project.dto.responseDto.ResponseDto;
import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Member;
import com.example.mini_project.entity.RefreshToken;
import com.example.mini_project.entity.MemberDetailsImpl;
import com.example.mini_project.repository.BoardRepository;
import com.example.mini_project.repository.MemberRepository;
import com.example.mini_project.repository.RefreshTokenRepository;
import com.example.mini_project.security.jwt.JwtUtil;
import com.example.mini_project.security.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseDto<?> signup(MemberRequestDto memberRequestDto) {

        if(memberRepository.findByName(memberRequestDto.getName()).isPresent()){
            return ResponseDto.fail("DUPLICATED_MEMBER", "존재하는 ID 입니다.");
        }

        Member member = Member.builder()
                .name(memberRequestDto.getName())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .build();
        memberRepository.save(member);
        return ResponseDto.success("회원가입 완료");
    }

    @Transactional
    public ResponseDto<?> login(MemberRequestDto memberRequestDto, HttpServletResponse httpServletResponse) {

        Member member = memberRepository.findByName(memberRequestDto.getName()).orElseThrow(
                () -> new RuntimeException("Not found account")
        );

        if(!passwordEncoder.matches(memberRequestDto.getPassword(), member.getPassword())){
            return ResponseDto.fail("NOT_MATCH_PASSWORD", "비밀번호가 일치하지 않습니다.");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(memberRequestDto.getName());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByName(memberRequestDto.getName());

        if (refreshToken.isPresent()){
            refreshTokenRepository.save(refreshToken.get().update(tokenDto.getRefreshToken()));
        }else {
            RefreshToken renewRefreshToken = new RefreshToken(tokenDto.getRefreshToken(), memberRequestDto.getName());
            refreshTokenRepository.save(renewRefreshToken);
        }

        setHeader(httpServletResponse, tokenDto);
        return ResponseDto.success("로그인 성공");

    }

//    public ResponseDto<?> logout(HttpServletRequest httpServletRequest) {
//        if(!jwtUtil.refreshTokenValidation(httpServletRequest.getHeader("Refresh-Token"))){
//            throw new RuntimeException("Token has expired");
//        }
//        Member member = jwtUtil.getAccountFromAuthentication();
//        if (null == member){
//            throw new RuntimeException("Member Not Found");
//        }
//
//        jwtUtil.deleteRefreshToken(httpServletRequest.getHeader("Refresh-Token"));
//
//        return ResponseDto.success("로그아웃 완료");
//    }

    public ResponseDto<?> checkId(CheckIdRequestDto checkIdRequestDto) {
        if(memberRepository.findByName(checkIdRequestDto.getName()).isPresent()){
            return ResponseDto.fail("DUPLICATED_MEMBER", "존재하는 ID 입니다.");
        }else return ResponseDto.success("사용가능한 ID 입니다.");
    }

    public ResponseDto<?> getMypage(MemberDetailsImpl memberDetailsImpl) {

        Member member = isPresentMember(memberDetailsImpl);
        if (null == member){
            return ResponseDto.fail("NOT_FOUND","존재하지 않는 회원입니다.");
        }

        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardListResponseDto> boardListResponseDtoList = new ArrayList<>();
        for(Board board : boardList){
            BoardListResponseDto boardListResponseDto = new BoardListResponseDto(board);
            boardListResponseDtoList.add(boardListResponseDto);
        }
        return ResponseDto.success(
                MypageResponseDto.builder()
                        .name(member.getName())
                        .image(member.getImage())
                        .boardListResponseDtoList(boardListResponseDtoList)
                        .build()
        );
    }

    private static void setHeader(HttpServletResponse httpServletResponse, TokenDto tokenDto) {
        httpServletResponse.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        httpServletResponse.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(MemberDetailsImpl memberDetailsImpl){
        Optional<Member> member = memberRepository.findById(memberDetailsImpl.getMember().getId());
        return member.orElse(null);
    }

    @Transactional
    public ResponseDto<?> changeImage(ChangeMemberInfoRequestDto changeMemberInfoRequestDto, MemberDetailsImpl memberDetailsImpl) {

        Member member = memberRepository.findByName(memberDetailsImpl.getMember().getName()).orElseThrow(
                () -> new RuntimeException("Not found account")
        );

        member.updateInfo(changeMemberInfoRequestDto);
        return ResponseDto.success("이미지 변경 성공");
    }

    @Transactional
    public ResponseDto<?> changePassword(ChangeMemberInfoRequestDto changeMemberInfoRequestDto, MemberDetailsImpl memberDetailsImpl) {

        Member member = memberRepository.findByName(memberDetailsImpl.getMember().getName()).orElseThrow(
                () -> new RuntimeException("Not found account")
        );

        if(!passwordEncoder.matches(changeMemberInfoRequestDto.getCurrentPassword(), member.getPassword())){
            return ResponseDto.fail("NOT_MATCH_PASSWORD", "비밀번호가 일치하지 않습니다.");
        }

        if(!changeMemberInfoRequestDto.getCurrentPassword().equals(changeMemberInfoRequestDto.getModifiedPassword())){
            return ResponseDto.fail("NOT_MATCH_PASSWORD", "비밀번호가 일치하지 않습니다.");
        }

        member.updateInfo(changeMemberInfoRequestDto);
        return ResponseDto.success("비밀번호 변경 성공");
    }
}
