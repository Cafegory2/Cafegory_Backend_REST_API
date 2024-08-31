package com.example.demo.exception;

import com.example.demo.dto.auth.JwtClaims;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

public class JwtCustomException extends RuntimeException {

    private final ExceptionType exceptionType;
    private JwtClaims claims;

    public JwtCustomException(@NonNull ExceptionType exceptionType, @NonNull Throwable cause, @NonNull JwtClaims claims) {
        super(cause);
        this.exceptionType = exceptionType;
        this.claims = claims;
    }

    public JwtCustomException(@NonNull ExceptionType exceptionType, @NonNull Throwable cause) {
        super(cause);
        this.exceptionType = exceptionType;
    }

    public JwtCustomException(@NonNull ExceptionType exceptionType, @NonNull JwtClaims claims) {
        this.exceptionType = exceptionType;
        this.claims = claims;

    }

    public JwtCustomException(@NonNull ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public String getMessage() {
        return exceptionType.getErrorMessage();
    }

    public String getCauseMessage() {
        return getCause() != null ? getCause().getMessage() : "No cause available";
    }

    public HttpStatus getHttpStatus() {
        return exceptionType.getErrStatus();
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public Object getClaim(String key) {
        return claims.getClaim(key);
    }

    public JwtClaims getClaims() {
        return claims;
    }
}
