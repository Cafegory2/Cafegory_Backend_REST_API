package com.example.demo.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtErrorResponse {

	private final String errorMessage;
}
