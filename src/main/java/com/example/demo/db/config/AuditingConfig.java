package com.example.demo.db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.demo.time.TimeUtil;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "currentDateTimeProvider")
public class AuditingConfig {

	@Bean
	public CurrentDateTimeProvider currentDateTimeProvider(TimeUtil timeUtil) {
		return new CurrentDateTimeProvider(timeUtil);
	}
}
