package com.project.spboard.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDto {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String role;
}
