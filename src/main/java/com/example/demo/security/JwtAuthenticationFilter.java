package com.example.demo.security;

import com.example.demo.exception.JwtTokenAuthenticationException;
import com.example.demo.implement.token.JwtClaims;
import com.example.demo.implement.tokenmanagerment.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    private final JwtTokenManager jwtTokenManager;
    private final JpaUserDetailsService jpaUserDetailsService;

    // 토큰 검증을 하지 말아야할 url을 등록한다.
    private final List<String> excludeUrls = List.of(
        "/favicon.ico",
        "/login",
        "/docs",
        "/auth/refresh"
    );


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("Requested URI: {}", request.getRequestURI());
        boolean matchesUrl = excludeUrls.stream()
            .anyMatch(url -> request.getRequestURI().startsWith(url));
        if (matchesUrl) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");

        if (isValidAuthorizationHeader(authorization)) {
            String jwtToken = extractJwtAccessToken(authorization);
            processTokenAuthentication(jwtToken);
            filterChain.doFilter(request, response);
        } else {
            throw new JwtTokenAuthenticationException(JWT_INVALID_FORMAT);
        }
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
