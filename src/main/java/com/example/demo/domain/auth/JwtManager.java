package com.example.demo.domain.auth;

import static com.example.demo.exception.ExceptionType.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.exception.CafegoryException;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public final class JwtManager {

    private final String secretKey;

    public JwtManager.JwtBuilder newTokenBuilder() {
        return new JwtManager.JwtBuilder(this.secretKey);
    }

    public static final class JwtBuilder {

        private final String secretKey;
        private final String type = "JWT";
        private final Map<String, Object> claims = new HashMap<>();
        private Date issuedAt = new Date();
        private int lifeTimeAsSeconds;

        public JwtBuilder(final String secretKey) {
            this.secretKey = secretKey;
        }

        public JwtManager.JwtBuilder addClaim(final String key, Object value) {
            this.claims.put(key, value);
            return this;
        }

        public JwtManager.JwtBuilder addAllClaims(final Map<String, Object> claims) {
            this.claims.putAll(claims);
            return this;
        }

        public JwtManager.JwtBuilder issuedAt(final Date issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public JwtManager.JwtBuilder lifeTimeAsSeconds(final int lifeTimeAsSeconds) {
            this.lifeTimeAsSeconds = lifeTimeAsSeconds;
            return this;
        }

        public String build() {
            return Jwts.builder()
                    .header().type(this.type).and()
                    .claims(this.claims)
                    .issuedAt(this.issuedAt)
                    .expiration(Date.from(this.issuedAt.toInstant().plusSeconds(this.lifeTimeAsSeconds)))
                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .compact();
        }
    }

    public Claims verifyAndExtractClaims(final String jwt) {
        try {
            Jws<Claims> jws = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(jwt);
            return jws.getPayload();
        } catch (ExpiredJwtException e) {
            throw new CafegoryException(JWT_EXPIRED);
        } catch (JwtException e) {
            log.error("JWT decode parser error: {}", e.getMessage());
            throw new CafegoryException(JWT_DESTROYED);
        }
    }
}
