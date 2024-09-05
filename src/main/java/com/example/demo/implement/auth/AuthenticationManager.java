package com.example.demo.implement.auth;

import static com.example.demo.exception.ExceptionType.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.exception.CafegoryException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationManager implements HandlerInterceptor {
	private final CafegoryTokenManager cafegoryTokenManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String rawMethod = request.getMethod();
		HttpMethod requestedMethod = HttpMethod.valueOf(rawMethod);
		if (requestedMethod.equals(HttpMethod.OPTIONS)) {
			return true;
		}
		try {
			String accessToken = request.getHeader("Authorization");
			accessToken = accessToken.replaceFirst("Bearer ", "");
			boolean isAccessToken = cafegoryTokenManager.isAccessToken(accessToken);
			if (!isAccessToken) {
				throw new CafegoryException(JWT_DESTROYED);
			}
			cafegoryTokenManager.getIdentityId(accessToken);
			return true;
		} catch (NullPointerException e) {
			throw new CafegoryException(TOKEN_NOT_FOUND);
		}
	}
}
