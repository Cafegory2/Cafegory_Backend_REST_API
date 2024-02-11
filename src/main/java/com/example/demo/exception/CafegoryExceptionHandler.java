package com.example.demo.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CafegoryExceptionHandler {
	@ExceptionHandler(CafegoryException.class)
	public ResponseEntity<ErrorResponse> handle(CafegoryException exception) {
		PrintWriter printWriter = new PrintWriter(new StringWriter());
		exception.printStackTrace(printWriter);
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return ResponseEntity.status(exception.getHttpStatus()).body(errorResponse);
	}
}
