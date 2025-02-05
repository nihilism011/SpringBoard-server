package com.project.spboard.board.entity;

import com.project.spboard.core.entity.BaseEntity;
import com.project.spboard.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
