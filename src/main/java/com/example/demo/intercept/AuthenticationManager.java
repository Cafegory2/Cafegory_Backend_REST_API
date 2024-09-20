//package com.example.demo.domain.auth;
//
//import static com.example.demo.domain.auth.TokenClaims.*;
//import static com.example.demo.exception.ExceptionType.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import io.jsonwebtoken.Claims;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import com.example.demo.exception.CafegoryException;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//public class AuthenticationManager implements HandlerInterceptor {
//
//    private final JwtManager jwtManager;
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//		String rawMethod = request.getMethod();
//		HttpMethod requestedMethod = HttpMethod.valueOf(rawMethod);
//		if (requestedMethod.equals(HttpMethod.OPTIONS)) {
//			return true;
//		}
//		try {
//			String accessToken = request.getHeader("Authorization");
//			accessToken = accessToken.replaceFirst("Bearer ", "");
//
//			jwtManager.validateClaim(
//                    accessToken, TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue());
//            Claims claims = jwtManager.verifyAndExtractClaims(accessToken);
//
//            request.setAttribute("memberId", claims.getSubject());
//            return true;
//		} catch (NullPointerException e) {
//			throw new CafegoryException(TOKEN_NOT_FOUND);
//		}
//	}
//}
