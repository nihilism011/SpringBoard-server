package com.project.spboard.member.entity;

import com.project.spboard.board.entity.Board;
import com.project.spboard.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = true)
    private String password;
    @Column(nullable = false,unique = true)
    private String name ;
    @Column(nullable = false)
    private String role = "USER";

    @OneToMany
    private List<Board> boardList;
}