package com.example.demo.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class BeanValidationExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		String errorSummary = errors.entrySet().stream()
			.map(entry -> entry.getKey() + ": " + entry.getValue())
			.collect(Collectors.joining(", "));

		PrintWriter printWriter = new PrintWriter(new StringWriter());
		exception.printStackTrace(printWriter);
		ErrorResponse errorResponse = new ErrorResponse(errorSummary);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
