package com.example.demo.factory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static com.example.demo.domain.auth.TokenClaims.*;

public class TestJwtFactory {

    public static String createAccessToken(Map<String, Object> claims, Date issuedAt, int lifeTimeAsSeconds, String testSecret) {
        return Jwts.builder()
                .header().type("JWT").and()
                .claims(claims)
                .issuedAt(issuedAt)
                .expiration(Date.from(issuedAt.toInstant().plusSeconds(lifeTimeAsSeconds)))
                .signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
                .compact();
    }

    public static String createAccessToken(Long memberId, String testSecret) {
        Date issuedAt = Date.from(Instant.now());
        return Jwts.builder()
                .header().type("JWT").and()
                .claim(TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue())
                .claim(SUBJECT.getValue(), String.valueOf(memberId))
                .issuedAt(issuedAt)
                .expiration(Date.from(issuedAt.toInstant().plusSeconds(3600)))
                .signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
                .compact();
    }

    public static String createRefreshToken(Long memberId, String testSecret) {
        Date issuedAt = Date.from(Instant.now());
        return Jwts.builder()
                .header().type("JWT").and()
                .claim(TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue())
                .claim(SUBJECT.getValue(), String.valueOf(memberId))
                .issuedAt(issuedAt)
                .expiration(Date.from(issuedAt.toInstant().plusSeconds(3600)))
                .signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
                .compact();
    }

    public static String createExpiredAccessToken(Long memberId, String testSecret) {
        Date issuedAt = Date.from(Instant.now());
        return Jwts.builder()
                .header().type("JWT").and()
                .claim(TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue())
                .claim(SUBJECT.getValue(), String.valueOf(memberId))
                .issuedAt(issuedAt)
                .expiration(Date.from(issuedAt.toInstant().plusSeconds(0)))
                .signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
                .compact();
    }

    public static String createExpiredRefreshToken(Long memberId, String testSecret) {
        Date issuedAt = Date.from(Instant.now());
        return Jwts.builder()
                .header().type("JWT").and()
                .claim(TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue())
                .claim(SUBJECT.getValue(), String.valueOf(memberId))
                .issuedAt(issuedAt)
                .expiration(Date.from(issuedAt.toInstant().plusSeconds(0)))
                .signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
                .compact();
    }
}
