package com.example.demo.implement.auth;

public enum TokenClaims {

    TOKEN_TYPE("tokenType"),
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken"),
    SUBJECT("sub"),
    ISSUED_AT("iat"),
    EXPIRATION_TIME("exp"),
    ROLE("role");

    private final String value;

    TokenClaims(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
