package com.example.demo.dto.auth;

import com.example.demo.implement.auth.TokenClaims;
import io.jsonwebtoken.Claims;

import java.util.Date;

public class JwtClaims {

    private final Claims claims;

    public JwtClaims(Claims claims) {
        this.claims = claims;
    }

    public String getSubject() {
        return claims.get(TokenClaims.SUBJECT.getValue(), String.class);
    }

    public Date getExpiration() {
        return claims.get(TokenClaims.EXPIRATION_TIME.getValue(), Date.class);
    }

    public String getClaim(String key) {
        return claims.get(key, String.class);
    }
}
