package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.domain.auth.AuthenticationManager;
import com.example.demo.domain.auth.CafegoryTokenManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
	private final CafegoryTokenManager cafegoryTokenManager;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticationManager(cafegoryTokenManager))
			.addPathPatterns("/**")
			.excludePathPatterns("/cafe/{cafeId:^[0-9]*$}/review/list", "/cafe/list/**", "/cafe/{cafeId:^[0-9]*$}",
				"/oauth2/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("http://localhost:3000", "https://cafegory.netlify.app")
			.allowCredentials(true)
			.allowedMethods("*");
	}
}
