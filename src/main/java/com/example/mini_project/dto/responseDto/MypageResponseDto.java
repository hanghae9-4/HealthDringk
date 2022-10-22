package com.example.mini_project.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {

    private String image;
    private String name;
    private List<BoardListResponseDto> boardListResponseDtoList;
}
