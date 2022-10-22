package com.example.mini_project.entity;

import com.example.mini_project.util.TimeStamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Board extends TimeStamped {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "member", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
