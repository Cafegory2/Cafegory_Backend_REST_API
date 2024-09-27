package com.example.demo.config;

import com.example.demo.implement.tokenmanagerment.JwtTokenManager;
import com.example.demo.security.JpaUserDetailsService;
import com.example.demo.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenManager jwtTokenManager;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final AuthenticationEntryPoint authEntryPoint;

    public SecurityConfig(JwtTokenManager jwtTokenManager, JpaUserDetailsService jpaUserDetailsService,
                          @Qualifier("jwtTokenAuthenticationEntrypoint") AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtTokenManager = jwtTokenManager;
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.authEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //JWT 토큰 검증을 하지 않으려면 anyMatchers에 url을 추가하고 JwtAuthenticationFilter 클래스 안에도 추가해야한다.
        http
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/docs/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/auth/refresh").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenManager, jpaUserDetailsService), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(authEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
            List.of("http://localhost:5173")
        );
        configuration.setAllowedMethods(
            List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
