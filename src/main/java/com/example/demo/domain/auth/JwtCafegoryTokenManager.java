package com.example.demo.domain.auth;

import java.time.Instant;
import java.util.Date;
import java.util.Map;


import com.example.demo.dto.auth.CafegoryToken;

import lombok.RequiredArgsConstructor;

import static com.example.demo.domain.auth.TokenClaims.*;

@RequiredArgsConstructor
public final class JwtCafegoryTokenManager {

    private static final int ACCESS_TOKEN_LIFETIME_SECONDS = 3600;
    private static final int REFRESH_TOKEN_LIFETIME_SECONDS = 3600 * 24 * 7;

    private final JwtManager jwtManager;

    public CafegoryToken createAccessAndRefreshToken(final Map<String, Object> memberInformation) {
        Date issuedAt = Date.from(Instant.now());
        String accessToken = createAccessToken(memberInformation, issuedAt);
        String refreshToken = createRefreshToken(memberInformation, issuedAt);

        return new CafegoryToken(accessToken, refreshToken);
    }

    public String createAccessToken(final Map<String, Object> memberInformation) {
        Date issuedAt = Date.from(Instant.now());
        return createAccessToken(memberInformation, issuedAt);
    }

    private String createAccessToken(final Map<String, Object> claims, final Date issuedAt) {
        return jwtManager.newTokenBuilder()
                .issuedAt(issuedAt)
                .lifeTimeAsSeconds(ACCESS_TOKEN_LIFETIME_SECONDS)
                .addAllClaims(claims)
                .addClaim(TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue())
                .build();
    }

    private String createRefreshToken(final Map<String, Object> claims, final Date issuedAt) {
        return jwtManager.newTokenBuilder()
                .issuedAt(issuedAt)
                .lifeTimeAsSeconds(REFRESH_TOKEN_LIFETIME_SECONDS)
                .addAllClaims(claims)
                .addClaim(TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue())
                .build();
    }
}
