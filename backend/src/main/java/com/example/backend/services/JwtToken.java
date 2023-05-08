package com.example.backend.services;

import com.example.backend.exeptions.ErrorAuth;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtToken {
    public String getToken() {
        return token;
    }

    public LocalDateTime getIssueAt() {
        return issueAt;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public Long getUserId() {
        return userId;
    }

    private final String token;
    private final LocalDateTime issueAt;
    private final LocalDateTime expiration;
    private final Long userId;

    private JwtToken(String token, LocalDateTime issueAt, LocalDateTime expiration, Long userId) {
        this.token = token;
        this.issueAt = issueAt;
        this.expiration = expiration;
        this.userId = userId;
    }

    public static JwtToken of(Long userId, Long validityInMinutes, String secretKey) {
        var issueDate = Instant.now();
        //SecretKey keys = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        var expiration = issueDate.plus(validityInMinutes, ChronoUnit.MINUTES);
        final String tokenStr = Jwts.builder()
                .claim("user_id", userId)
                .setIssuedAt(Date.from(issueDate))
                .setExpiration(Date.from(expiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
        return new JwtToken(
                tokenStr,
                LocalDateTime.ofInstant(issueDate, ZoneId.systemDefault()),
                LocalDateTime.ofInstant(expiration, ZoneId.systemDefault()),
                userId
        );
    }

    public static JwtToken from(String token, String accessTokenSecret) throws ErrorAuth {
        var claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return new JwtToken(
                token,
                LocalDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault()),
                claims.get("user_id", Long.class)
        );
    }

}
