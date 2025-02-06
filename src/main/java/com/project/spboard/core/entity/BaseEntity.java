package com.project.spboard.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners (AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column (nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column (nullable = false)
    private LocalDateTime modifiedAt;


}