package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.dto.ErrorResponse;

@ControllerAdvice
public class CafegoryExceptionHandler {
	@ExceptionHandler(CafegoryException.class)
	public ResponseEntity<ErrorResponse> handle(CafegoryException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return ResponseEntity.status(exception.getHttpStatus()).body(errorResponse);
	}
}
