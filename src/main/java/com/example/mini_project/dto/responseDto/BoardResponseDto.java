package com.example.mini_project.dto.responseDto;

import com.example.mini_project.entity.Board;
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

    private String name = "null";

    private Long heartNum;

    private boolean heartOrNot;

    private List<CommentResponseDto> commentResponseDtoList;
    private LocalDateTime createdAt;

    public BoardResponseDto (Board board){
        this.title = board.getTitle();
        this.content = board.getContent();
        this.image = board.getImage();
        this.writer = board.getMember().getName();
        this.createdAt = board.getCreatedAt();
    }

}
