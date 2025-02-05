package com.project.spboard.board.entity;

import com.project.spboard.core.entity.BaseEntity;
import com.project.spboard.member.entity.Member;
import jakarta.persistence.*;
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
public class Board extends BaseEntity {
    @Column (nullable = false)
    private String title;
    @Column (nullable = false)
    private String contents;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;
    @OneToMany
    private List<Comment> comments;
}
