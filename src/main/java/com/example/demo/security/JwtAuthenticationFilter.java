package com.example.demo.security;

import com.example.demo.exception.JwtCustomException;
import com.example.demo.implement.token.JwtClaims;
import com.example.demo.implement.tokenmanagerment.JwtTokenManager;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.demo.exception.ExceptionType.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    private final JwtTokenManager jwtTokenManager;
    private final JpaUserDetailsService jpaUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // JWT토큰이 유효하지 않거나 토큰이 만료된 경우 등 "인증"과 관련된 문제라면 AuthenticationException을 던져야한다.
        // 사용자는 인증이되었지만, 해당 요청에 필요한 권한이 부족한 경우 AccessDeniedException을 던져야한다.
        String authorization = request.getHeader("Authorization");
        try {
            if (isValidAuthorizationHeader(authorization)) {
                String jwtToken = extractJwtToken(authorization);
                processTokenAuthentication(jwtToken);
            } else {
                throw new BadCredentialsException(JWT_INVALID_FORMAT.getErrorMessage());
            }
        } catch (JwtCustomException e) {
            if(e.getExceptionType() == JWT_EXPIRED) {
                throw new AccountExpiredException(JWT_EXPIRED.getErrorMessage());
            }
        } catch (JwtException e) {
            throw new BadCredentialsException(JWT_DESTROYED.getErrorMessage());
        }

        filterChain.doFilter(request, response);
    }

    private void processTokenAuthentication(String jwtToken) {
        JwtClaims jwtClaims = jwtTokenManager.verifyAndExtractClaims(jwtToken);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String memberId = jwtClaims.getSubject();

            CustomUserDetails userDetails = jpaUserDetailsService.loadUserByUserId(memberId);

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String extractJwtToken(String authorization) {
        return authorization.substring(BEARER.length());
    }

    private boolean isValidAuthorizationHeader(String authorization) {
        return authorization != null && authorization.startsWith(BEARER);
    }
}
