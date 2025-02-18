package com.project.spboard.core.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.spboard.core.dto.ApiResponse;
import com.project.spboard.core.security.CustomUserDetails;
import com.project.spboard.member.dto.LoginResDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/login") ||
            path.startsWith("/join") ||
            path.startsWith("/refresh");
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getAccessToken(request);

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //        토큰 만료 검사
        if (jwtUtil.isExpired(accessToken)) {
            System.out.println("accessToken is expired");
            SecurityContextHolder.clearContext();

            String jsonResponse = objectMapper.writeValueAsString(new ApiResponse(
                "error",
                "Access token is expired",
                null
            ));
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);

            return;
        }

        LoginResDto loginResDto = new LoginResDto(
            jwtUtil.getEmail(accessToken),
            jwtUtil.getName(accessToken),
            null,
            jwtUtil.getRoles(accessToken)
        );
        CustomUserDetails userDetails = new CustomUserDetails(loginResDto);

        Authentication authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }

    private String getAccessToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");

            return token.startsWith("Bearer ") ? token.split(" ")[1] : null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
