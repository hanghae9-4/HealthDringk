package com.example.mini_project.dto.responseDto;

import com.example.mini_project.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardListResponseDto {

    private Long id;
    private Category category;
    private String image;
    private String title;
    private String content;
    private String writer;
    private long heartNum;
    private LocalDateTime createdAt;

}
