package com.example.mini_project.entity;

import com.example.mini_project.dto.requestDto.CommentRequestDto;
import com.example.mini_project.util.TimeStamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;


    public boolean validateMember(Member member) {

        return !this.member.equals(member);
    }


    public void update(CommentRequestDto commentRequestDto) {

        this.content = commentRequestDto.getComment();

    }

}
