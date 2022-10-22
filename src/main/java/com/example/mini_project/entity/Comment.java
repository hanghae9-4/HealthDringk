package com.example.mini_project.entity;

import com.example.mini_project.util.TimeStamped;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "memberId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "boardId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
