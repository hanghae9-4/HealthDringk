package com.example.mini_project.dto.requestDto;

import com.example.mini_project.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class BoardRequestDto {

    private String title;
    private String image;
    private String content;
    private Category category;

}
