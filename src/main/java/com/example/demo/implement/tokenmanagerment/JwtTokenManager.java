package com.example.demo.implement.tokenmanagerment;

import static com.example.demo.exception.ExceptionType.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.exception.JwtTokenAuthenticationException;
import com.example.demo.implement.token.JwtClaims;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public final class JwtTokenManager {

    private final String secretKey;

    public JwtTokenManager.JwtBuilder newTokenBuilder() {
        return new JwtTokenManager.JwtBuilder(this.secretKey);
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

        public JwtTokenManager.JwtBuilder addClaim(final String key, Object value) {
            this.claims.put(key, value);
            return this;
        }

        public JwtTokenManager.JwtBuilder addAllClaims(final Map<String, Object> claims) {
            this.claims.putAll(claims);
            return this;
        }

        public JwtTokenManager.JwtBuilder issuedAt(final Date issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public JwtTokenManager.JwtBuilder lifeTimeAsSeconds(final int lifeTimeAsSeconds) {
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

    public JwtClaims verifyAndExtractClaims(final String jwt) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(jwt);
            return convertClaimsToJwtClaims(jws.getPayload());
        } catch (ExpiredJwtException e) {
            throw new JwtTokenAuthenticationException(JWT_EXPIRED, e, convertClaimsToJwtClaims(e.getClaims()));
        } catch (JwtException e) {
            throw new JwtTokenAuthenticationException(JWT_INVALID, e);
        }
    }

    private JwtClaims convertClaimsToJwtClaims(Claims claims) {
        return new JwtClaims(claims);
    }

    public void validateClaim(final String jwt, final String key, final String value) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .require(key, value)
                    .build()
                    .parse(jwt);
        } catch (ExpiredJwtException e) {
            throw new JwtTokenAuthenticationException(JWT_EXPIRED, e, convertClaimsToJwtClaims(e.getClaims()));
        } catch (InvalidClaimException e) {
            throw new JwtTokenAuthenticationException(JWT_CLAIM_INVALID, e, convertClaimsToJwtClaims(e.getClaims()));
        } catch (JwtException e) {
            throw new JwtTokenAuthenticationException(JWT_INVALID, e);
        }
    }
}
