package com.example.mini_project.entity;


import com.example.mini_project.dto.requestDto.ChangeMemberInfoRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column
    private String image;

    public void updateInfo(ChangeMemberInfoRequestDto changeMemberInfoRequestDto){
        this.name = this.getName();
        this.password = changeMemberInfoRequestDto.getModifiedPassword() != null ? this.getPassword() : changeMemberInfoRequestDto.getModifiedPassword();
        this.image = changeMemberInfoRequestDto.getImage() != null ? this.getImage() : changeMemberInfoRequestDto.getImage();
    }

}
