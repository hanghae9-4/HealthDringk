package com.example.mini_project.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(name, password);
    }

}








