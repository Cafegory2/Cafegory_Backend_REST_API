package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.trash.implement.token.JwtClaims;

import lombok.Getter;

@Getter
public class JwtTokenAuthenticationException extends RuntimeException {

	private final ExceptionType exceptionType;
	private JwtClaims claims;

	public JwtTokenAuthenticationException(ExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

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

	public String getCauseMessage() {
		return getCause() != null ? getCause().getMessage() : "No cause available";
	}

	public HttpStatus getHttpStatus() {
		return exceptionType.getErrStatus();
	}
}
