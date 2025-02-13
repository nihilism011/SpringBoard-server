package com.project.spboard.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDto {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    private String password;
    @NotBlank
    private List<String> roles;
}
