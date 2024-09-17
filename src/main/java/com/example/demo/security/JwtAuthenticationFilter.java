package com.example.demo.security;

import com.example.demo.exception.JwtCustomException;
import com.example.demo.implement.token.JwtClaims;
import com.example.demo.implement.tokenmanagerment.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.example.demo.exception.ExceptionType.*;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    private final JwtTokenManager jwtTokenManager;
    private final JpaUserDetailsService jpaUserDetailsService;

    // 토큰 검증을 하지 말아야할 url을 등록한다.
    private final List<String> excludeUrls = List.of(
        "/login",
        "/docs",
        "/auth/refresh"
    );


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 로그인 로직은 JWT 토큰 검증을 하면 안된다.
        boolean matchesUrl = excludeUrls.stream()
            .anyMatch(url -> request.getRequestURI().startsWith(url));
        if (matchesUrl) {
            filterChain.doFilter(request, response);
            return;
        }
        //TODO 커스텀 토큰 예외도 분리하자.
        String authorization = request.getHeader("Authorization");

        if (isValidAuthorizationHeader(authorization)) {
            String jwtToken = extractJwtAccessToken(authorization);
            processTokenAuthentication(jwtToken);
        } else {
            throw new JwtCustomException(JWT_INVALID_FORMAT);
        }

        filterChain.doFilter(request, response);
    }

    private void processTokenAuthentication(final String jwtToken) {
        JwtClaims jwtClaims = jwtTokenManager.verifyAndExtractClaims(jwtToken);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String memberId = jwtClaims.getSubject();

            CustomUserDetails userDetails = jpaUserDetailsService.loadUserByUserId(memberId);

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String extractJwtAccessToken(final String authorization) {
        return authorization.substring(BEARER.length());
    }

    private boolean isValidAuthorizationHeader(final String authorization) {
        return authorization != null && authorization.startsWith(BEARER);
    }
}
