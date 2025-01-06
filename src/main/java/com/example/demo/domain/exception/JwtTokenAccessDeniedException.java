package com.example.demo.domain.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NonNull;

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
