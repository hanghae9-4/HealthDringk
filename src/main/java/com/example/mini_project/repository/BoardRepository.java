package com.example.mini_project.repository;


import com.example.mini_project.entity.Board;
import com.example.mini_project.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByCreatedAtDesc();

    List<Board> findAllByCategory(Category category);

    Page<Board> findAllByCategory(Category category, Pageable pageable);


}
