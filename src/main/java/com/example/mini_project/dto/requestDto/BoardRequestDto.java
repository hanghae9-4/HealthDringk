package com.example.mini_project.dto.requestDto;

import com.example.mini_project.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class BoardRequestDto {

    private MultipartFile image;
    private String title;
    private String content;
    private Category category;

}
