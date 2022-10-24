package com.example.mini_project.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {

    private String title;
    private String image;
    private String content;
    private String writer;
    private String name;
    private long heartNum;
    private List<CommentResponseDto> commentResponseDtoList;
    private LocalDateTime createdAt;

}
