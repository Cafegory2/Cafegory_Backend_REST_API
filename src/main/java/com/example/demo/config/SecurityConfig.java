package com.example.demo.config;

import com.example.demo.implement.tokenmanagerment.JwtTokenManager;
import com.example.demo.security.JpaUserDetailsService;
import com.example.demo.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenManager jwtTokenManager;
    private final JpaUserDetailsService jpaUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // JWT 토큰을 검증하는 필터이다. anyMatchers에 등록된 url은 JWT토큰을 검증하지 않는다.
        http
            .authorizeRequests(authorize -> authorize
                .antMatchers("/docs/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/auth/refresh").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenManager, jpaUserDetailsService), SecurityContextHolderFilter.class);

        return http.build();
    }

}
