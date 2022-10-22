package com.example.mini_project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn (name = "boardId", nullable = false)
    private Board board;

//    public boolean validateMember(Member member){
//        return !this.member.equals(member);
//    }

}
