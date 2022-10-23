package com.example.mini_project.security.jwt;

import com.example.mini_project.entity.RefreshToken;
import com.example.mini_project.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;

    public static final String ACCESS_TOKEN = "Access-Token";
    public static final String REFRESH_TOKEN = "Refresh-Token";
    private static final long ACCESS_TIME = 60000 * 1000L;
    private static final long REFRESH_TIME = 100000 * 1000L;


    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String getHeaderToken(HttpServletRequest request, String type){
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
    }

    public TokenDto createAllToken(String name){
        return new TokenDto(createToken(name,"Access"), createToken(name,"Refresh"));
    }

    public String createToken(String name, String type){

        Date date = new Date();
        long time = type.equals("Access") ? ACCESS_TIME : REFRESH_TIME;

        return Jwts.builder()
                .setSubject(name)
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date)
                .signWith(key,signatureAlgorithm)
                .compact();
    }

    public Boolean tokenValidation(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    public Boolean refreshTokenValidation(String token){

        //1차 검증
        if(!tokenValidation(token))return false;

        //DB와 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByName(getNameFromToken(token));

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());

    }

    public Authentication createAuthentication(String name){
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getNameFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    @Transactional(readOnly = true)
    public RefreshToken isPresentRefreshToken(String token){
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByName(getNameFromToken(token));
        return optionalRefreshToken.orElse(null);
    }

//    @Transactional
//    public ResponseDto<?> deleteRefreshToken(String token){
//        RefreshToken refreshToken = isPresentRefreshToken(token);
//        if (null == refreshToken){
//            throw new RuntimeException("Token Not Found");
//        }
//
//        refreshTokenRepository.delete(refreshToken);
//        return ResponseDto.success("로그아웃 완료");
//    }

}
