package com.project.spboard.core.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {


    @Value("${spring.jwt.secret}")
    private String secret;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    private Claims jwtClaims(String token) {

        //TODO 에러처리 필요할듯?
        return Jwts.parser()
            .verifyWith(secretKey).
            build().
            parseSignedClaims(token)
            .getPayload();
    }

    public String getEmail(String token) {
        return jwtClaims(token)
            .get("email", String.class);
    }

    public String getName(String token) {
        return jwtClaims(token)
            .get("name", String.class);
    }

    public List<String> getRoles(String token) {
        Claims claims = jwtClaims(token);
        List<?> roles = claims.get("roles", List.class);

        if (roles == null) return Collections.emptyList();

        return roles.stream()
            .filter(role -> role instanceof String)
            .map(role -> (String) role)
            .toList();
    }

    public boolean isExpired(String token) {
        LocalDateTime now = LocalDateTime.now();
        try {
            return jwtClaims(token)
                .getExpiration()
                .before(toDate(now));
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String createAccessToken(String email, String name, List<String> roles) {
        return createToken(email, name, roles, 2);
    }

    public String createRefreshToken(String email, String name, List<String> roles) {
        return createToken(email, name, roles, 14 * 60 * 60);
    }


    private String createToken(String email, String name, List<String> roles, long expiredMinute) {
        LocalDateTime now = LocalDateTime.now();
        return Jwts.builder()
            .claim("email", email)
            .claim("name", name)
            .claim("roles", roles)
            .issuedAt(toDate(now))
            .expiration(toDate(now.plusMinutes(expiredMinute)))
            .signWith(secretKey)
            .compact();
    }

    private Date toDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }
}
