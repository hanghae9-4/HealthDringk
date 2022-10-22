package com.example.mini_project.dto.responseDto;

import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardListResponseDto {

    private Long id;
    private Category category;
    private String image;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;

    public BoardListResponseDto(Board board){
        this.id = board.getId();
        this.category = board.getCategory();
        this.image = board.getImage();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getMember().getName();
        this.createdAt = board.getCreatedAt();
    }


}
