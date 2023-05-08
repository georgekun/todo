package com.example.backend.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class Token {
    // сам токен будет в этом поле
    private final String token;

   private Token(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    //метод создания токен на основе id, времени и секретного ключа
    public static Token of(Long userId,Long validityMinutes,String secretKey){
        var issueDate = Instant.now();
        return new Token(
                Jwts.builder()
                    .claim("user_id",userId)
                    .setIssuedAt(Date.from(issueDate))
                    .setExpiration(Date.from(issueDate.plus(validityMinutes,
                            ChronoUnit.MINUTES)))
                    .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact());
    }
}
