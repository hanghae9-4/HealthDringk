package com.example.mini_project.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeMemberInfoRequestDto {

    private String image;

    private String currentPassword;

    private String modifiedPassword;

}
