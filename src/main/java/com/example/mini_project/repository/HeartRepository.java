package com.example.mini_project.repository;

import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Heart;
import com.example.mini_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findHeartByMemberAndBoardId(Member member, Long boardId);
    void deleteHeartByMemberAndBoard(Member member, Board board);
}
