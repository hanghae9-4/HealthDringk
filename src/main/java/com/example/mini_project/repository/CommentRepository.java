package com.example.mini_project.repository;


import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBoardOrderByCreatedAtDesc(Board board);
}
