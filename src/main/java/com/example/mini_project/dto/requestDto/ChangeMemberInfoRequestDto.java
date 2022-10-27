package com.example.mini_project.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ChangeMemberInfoRequestDto {

    private MultipartFile image;

    private String currentPassword;

    private String modifiedPassword;

}
