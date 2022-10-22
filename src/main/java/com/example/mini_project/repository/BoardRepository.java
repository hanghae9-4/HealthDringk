package com.example.mini_project.repository;

import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Board> findAllByMemberOrderByCreatedAtDesc(Member member);
}
