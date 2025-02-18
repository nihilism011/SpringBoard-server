package com.project.spboard.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.spboard.core.dto.ApiResponse;
import com.project.spboard.core.security.jwt.JwtUtil;
import com.project.spboard.core.security.jwt.RefreshService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RefreshService refreshService;
    private final ObjectMapper objectMapper;
    @Value ("${spring.security.cookie.secure}")
    private boolean isSecured;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomUserDetails authPrincipal = (CustomUserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<GrantedAuthority> auth = (Iterator<GrantedAuthority>) authorities.iterator();

        List<String> roles = new ArrayList<String>();

        while (auth.hasNext()) {
            roles.add(auth.next().getAuthority());
        }
        String accessToken = jwtUtil.createAccessToken(authPrincipal.getUsername(), authPrincipal.getName(), roles);
        String refreshToken = jwtUtil.createRefreshToken(
            authPrincipal.getUsername(),
            authPrincipal.getName(),
            roles
        );
        refreshService.storeRefreshToken(authPrincipal.getUsername(), refreshToken);
        response.addCookie(createCookie("Authorization", refreshToken));

        String jsonResponse = objectMapper.writeValueAsString(new ApiResponse("success", "요청이 성공했습니다.", accessToken));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);

    }

    private Cookie createCookie(String key, String token) {

        Cookie cookie = new Cookie(key, token);
        cookie.setMaxAge(14 * 24 * 60 * 60);
        cookie.setSecure(isSecured);
        cookie.setPath("/");
        cookie.isHttpOnly();

        return cookie;

    }
}
