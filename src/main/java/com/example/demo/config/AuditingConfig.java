package com.example.demo.config;

import com.example.demo.util.TimeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "currentDateTimeProvider")
public class AuditingConfig {

    @Bean
    public CurrentDateTimeProvider currentDateTimeProvider(TimeUtil timeUtil) {
        return new CurrentDateTimeProvider(timeUtil);
    }
}
