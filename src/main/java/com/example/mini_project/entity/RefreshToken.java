package com.example.mini_project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String refreshToken;

    @NotBlank
    private String name;

    public RefreshToken(String token, String name){
        this.refreshToken = token;
        this.name = name;
    }

    public RefreshToken update(String token){
        this.refreshToken = token;
        return this;
    }

}
