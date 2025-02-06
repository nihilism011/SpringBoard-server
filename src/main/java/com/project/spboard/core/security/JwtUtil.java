package com.project.spboard.core.security;

import com.project.spboard.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey key;

    @Value("${security.jwt.expirationTime}")
    private long expirationTime;

    public JwtUtil(@Value ("${security.jwt.secretKey}") String secretKey) {
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public String generateToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", member.getRole());
        claims.put("name", member.getName());
        claims.put("id", member.getId());
        return Jwts.builder()
                .signWith(key)
                .subject(member.getEmail())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.warn("잘못된 JWT 서명.");
            throw new RuntimeException("Invalid JWT signature", e);
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰");
            throw new RuntimeException("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰");
            throw new RuntimeException("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 잘못됨.");
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
        public Long extractMemberId (String token){
            return extractClaims(token).get("id", Long.class);
        }

        public String extractEmail (String token){
            return extractClaims(token).getSubject();
        }

        public String extractName (String token){
            return extractClaims(token).get("name", String.class);
        }

        public String extractRole (String token){
            return extractClaims(token).get("role", String.class);
        }

        public boolean isTokenExpired (String token){
            return extractClaims(token).getExpiration().before(new Date());
        }

        public boolean validateToken (String token){
            return !isTokenExpired(token);
        }
    }
