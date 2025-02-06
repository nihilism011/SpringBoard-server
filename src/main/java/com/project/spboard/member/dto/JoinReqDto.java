package com.project.spboard.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinReqDto {
    @NotBlank
    private String email;

    private String password;
    @NotBlank
    private String name;
}
