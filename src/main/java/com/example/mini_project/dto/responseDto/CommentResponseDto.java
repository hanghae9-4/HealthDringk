package com.example.mini_project.dto.responseDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String image;
    private String writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
