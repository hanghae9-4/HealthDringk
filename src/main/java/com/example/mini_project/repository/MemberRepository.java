package com.example.mini_project.repository;

import com.example.mini_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsById(Long id);

    Optional<Member> findByName(String name);
}
