package com.example.demo.security;

import com.example.demo.exception.JwtTokenAuthenticationException;
import com.example.demo.implement.token.JwtClaims;
import com.example.demo.implement.tokenmanagerment.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.example.demo.exception.ExceptionType.*;
import static org.springframework.http.HttpMethod.*;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

    private final JwtTokenManager jwtTokenManager;
    private final JpaUserDetailsService jpaUserDetailsService;

    private static final MultiValueMap<String, String> excludeUrls = new LinkedMultiValueMap<>();

    static {
        excludeUrls.add(GET.name(), "/favicon.ico");
        excludeUrls.add(GET.name(), "/docs");
        excludeUrls.add(GET.name(), "/login");
        excludeUrls.add(GET.name(), "/cafe-studies");
        excludeUrls.add(GET.name(), "/cafes");
        excludeUrls.add(POST.name(), "/auth/refresh");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("Requested URI: {}", request.getRequestURI());

        if (shouldSkipTokenVerification(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader(AUTHORIZATION);

        if (isValidAuthorizationHeader(authorization)) {
            String jwtToken = extractJwtAccessToken(authorization);
            processTokenAuthentication(jwtToken);
            filterChain.doFilter(request, response);
        } else {
            throw new JwtTokenAuthenticationException(JWT_INVALID_FORMAT);
        }
    }

    private boolean shouldSkipTokenVerification(HttpServletRequest request) {
        List<String> urlsForMethod = excludeUrls.getOrDefault(request.getMethod(), Collections.emptyList());

        return urlsForMethod.stream()
            .anyMatch(url -> request.getRequestURI().startsWith(url));
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
