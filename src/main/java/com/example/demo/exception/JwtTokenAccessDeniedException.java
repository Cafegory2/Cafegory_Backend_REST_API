package com.example.demo.exception;

import com.example.demo.implement.token.JwtClaims;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Getter
public class JwtTokenAccessDeniedException extends RuntimeException {

    private final ExceptionType exceptionType;

    public JwtTokenAccessDeniedException(@NonNull ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public String getMessage() {
        return exceptionType.getErrorMessage();
    }

    public HttpStatus getHttpStatus() {
        return exceptionType.getErrStatus();
    }
}
