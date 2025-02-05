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
public class Comment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board boardNo;
    @Column(nullable = false)
    private String contents;
    @ManyToOne
    @JoinColumn(name = "author")
    private Member author;
    @ManyToOne
    @JoinColumn(name = "parent_no")
    private Comment parentNo;
}
