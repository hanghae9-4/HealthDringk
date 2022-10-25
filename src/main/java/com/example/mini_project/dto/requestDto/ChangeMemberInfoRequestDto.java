package com.example.mini_project.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class ChangeMemberInfoRequestDto {

    private MultipartFile image;

    private String currentPassword;

    private String modifiedPassword;

}
