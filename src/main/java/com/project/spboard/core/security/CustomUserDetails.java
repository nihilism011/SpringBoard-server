package com.project.spboard.core.security;

import com.project.spboard.member.dto.LoginResDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final LoginResDto loginResDto;

    public CustomUserDetails(LoginResDto loginResDto) {
        this.loginResDto = loginResDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return loginResDto.getRoles()
            .stream().map(
                role -> new SimpleGrantedAuthority(role)
            ).toList();

    }

    @Override
    public String getPassword() {
        return loginResDto.getPassword();
    }

    @Override
    public String getUsername() {
        return loginResDto.getEmail();
    }

    public String getName() {
        return loginResDto.getName();
    }

}
