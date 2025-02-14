package com.project.spboard.core.security;

import com.project.spboard.member.entity.Member;
import com.project.spboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Member not found"));

        return new CustomUserDetails(member.toLoginResDto());
    }
}
