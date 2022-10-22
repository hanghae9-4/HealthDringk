package com.example.mini_project.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class ChangeImageRequestDto {

    @NotBlank
    private String image;

}
