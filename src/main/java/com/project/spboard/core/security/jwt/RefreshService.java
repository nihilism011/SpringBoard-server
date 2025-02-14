package com.project.spboard.core.security.jwt;

import com.project.spboard.core.dto.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RefreshService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtUtil jwtUtil;

    public ResponseEntity<ApiResponse<String>> validateRefreshToken(HttpServletRequest request) {
        String tokenFromCookie = getRefreshTokenFromCookie(request);

        String tokenFromRedis = findRefreshToken(jwtUtil.getEmail(tokenFromCookie));

        if (jwtUtil.isExpired(tokenFromRedis) || !tokenFromRedis.equals(tokenFromCookie)) {
            return ApiResponse.error("Token is not valid", HttpStatus.UNAUTHORIZED);
        } else {
            String newAccessToken = jwtUtil.createAccessToken(
                jwtUtil.getEmail(tokenFromCookie),
                jwtUtil.getName(tokenFromCookie),
                jwtUtil.getRoles(tokenFromCookie)
            );
            return ApiResponse.success(newAccessToken);
        }


    }

    public void storeRefreshToken(String email, String token) {
        redisTemplate.opsForValue().set("refresh: " + email, token, Duration.ofDays(14));
    }

    private void deleteRefreshToken(String email) {
        redisTemplate.delete("refresh: " + email);
    }

    private String findRefreshToken(String email) {
        Object token = redisTemplate.opsForValue().get("refresh: " + email);
        return (token != null) ? token.toString() : null;
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        try {
            final Cookie[] cookies = request.getCookies();

            String token = Arrays.stream(cookies)
                .filter(cookie -> "Authorization".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

            return token;
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

}
