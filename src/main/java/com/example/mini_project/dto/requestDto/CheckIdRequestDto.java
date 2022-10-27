package com.example.mini_project.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckIdRequestDto {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{2,10}$")
    private String name;

}
