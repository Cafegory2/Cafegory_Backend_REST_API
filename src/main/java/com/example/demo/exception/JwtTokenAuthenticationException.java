package com.example.demo.exception;

import com.example.demo.implement.token.JwtClaims;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Getter
public class JwtTokenAuthenticationException extends RuntimeException {

    private final ExceptionType exceptionType;
    private JwtClaims claims;

    public JwtTokenAuthenticationException(ExceptionType exceptionType, Throwable cause, JwtClaims claims) {
        super(cause);
        this.exceptionType = exceptionType;
        this.claims = claims;
    }

    public JwtTokenAuthenticationException(ExceptionType exceptionType, Throwable cause) {
        super(cause);
        this.exceptionType = exceptionType;
    }

    public JwtTokenAuthenticationException(ExceptionType exceptionType, JwtClaims claims) {
        this.exceptionType = exceptionType;
        this.claims = claims;
    }

    @Override
    public String getMessage() {
        return exceptionType.getErrorMessage();
    }

    public HttpStatus getHttpStatus() {
        return exceptionType.getErrStatus();
    }
}
