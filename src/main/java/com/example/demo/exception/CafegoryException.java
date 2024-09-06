package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import lombok.NonNull;

public class CafegoryException extends RuntimeException {
	private final ExceptionType exceptionType;

	public CafegoryException(@NonNull ExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

	@Override
	public String getMessage() {
		return exceptionType.getErrorMessage();
	}

	public HttpStatus getHttpStatus() {
		return exceptionType.getErrStatus();
	}

	public ExceptionType getExceptionType() {return exceptionType;}
}
