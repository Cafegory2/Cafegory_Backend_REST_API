package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class JwtTokenExceptionHandler {

	@ExceptionHandler(JwtTokenAuthenticationException.class)
	public ResponseEntity<ErrorResponse> handle(JwtTokenAuthenticationException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return ResponseEntity.status(exception.getHttpStatus()).body(errorResponse);
	}

	@ExceptionHandler(JwtTokenAccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handle(JwtTokenAccessDeniedException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return ResponseEntity.status(exception.getHttpStatus()).body(errorResponse);
	}
}
