package com.project.spboard.core.security;

import com.project.spboard.member.dto.LoginReqDto;
import com.project.spboard.member.dto.LoginResDto;
import com.project.spboard.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private Member member;

    CustomUserDetails(Member member){
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                (GrantedAuthority) () -> member.getRole()
        );
    }
    @Override
    public String getPassword() {
        return member.getPassword();
    }
    @Override
    public String getUsername() {
        return member.getEmail();
    }
    public String getName() {
        return member.getName();
    }
    public String getRole() {
        return member.getRole();
    }

}
