package com.example.demo.builder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtTokenBuilder {

    private static final String TEST_SECRET = "01234567890123456789012345678901234567890123456789";
    private static final String JWT = "JWT";

    private Map<String, Object> claims = Map.of("a", "a", "b", "b");
    private Date issuedAt = Date.from(Instant.now());
    private Date expiration = Date.from(issuedAt.toInstant().plusSeconds(3600));
    private Key key = Keys.hmacShaKeyFor(TEST_SECRET.getBytes());

    private JwtTokenBuilder() {
    }

    private JwtTokenBuilder(JwtTokenBuilder copy) {
        this.claims = copy.claims;
        this.issuedAt = copy.issuedAt;
        this.expiration = copy.expiration;
        this.key = copy.key;
    }

    public JwtTokenBuilder but() {
        return new JwtTokenBuilder(this);
    }

    public static JwtTokenBuilder aToken() {
        return new JwtTokenBuilder();
    }

    public JwtTokenBuilder withClaims(Map<String, Object> claims) {
        this.claims = claims;
        return this;
    }

    public JwtTokenBuilder withIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
        return this;
    }

    public JwtTokenBuilder withExpiration(long lifeTimeAsSeconds) {
        this.expiration = Date.from(Instant.now().plusSeconds(lifeTimeAsSeconds));
        return this;
    }

    public JwtTokenBuilder withKey(String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return this;
    }

    public String build() {
        return Jwts.builder()
                .header().type(JWT).and()
                .claims(claims)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }
}
