package com.example.demo.domain.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationManager implements HandlerInterceptor {
	private final CafegoryTokenManager cafegoryTokenManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		try {
			String accessToken = request.getHeader("Authorization");
			accessToken = accessToken.replaceFirst("Bearer ", "");
			cafegoryTokenManager.getIdentityId(accessToken);
		} catch (NullPointerException e) {
			sendErrorResponse(response, "토큰이 없습니다.");
			return false;
		} catch (IllegalArgumentException e) {
			sendErrorResponse(response, e.getMessage());
			return false;
		}
		return true;
	}

	private static void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}
