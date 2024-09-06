package com.example.demo.exception;

import com.example.demo.dto.auth.JwtClaims;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtCustomException extends RuntimeException {

    private final ExceptionType exceptionType;
    private JwtClaims claims;

    public JwtCustomException(ExceptionType exceptionType, Throwable cause, JwtClaims claims) {
        super(cause);
        this.exceptionType = exceptionType;
        this.claims = claims;
    }

    public JwtCustomException(ExceptionType exceptionType, Throwable cause) {
        super(cause);
        this.exceptionType = exceptionType;
    }

    public JwtCustomException(ExceptionType exceptionType, JwtClaims claims) {
        this.exceptionType = exceptionType;
        this.claims = claims;

    }

    public JwtCustomException(ExceptionType exceptionType) {
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

    public Object getClaim(String key) {
        return claims.getClaim(key);
    }

}
