package com.example.demo.config;

import com.example.demo.util.TimeUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@TestConfiguration
@EnableJpaAuditing(dateTimeProviderRef = "currentDateTimeProvider")
public class TestAuditingConfig {

    @Bean
    public CurrentDateTimeProvider currentDateTimeProvider(TimeUtil timeUtil) {
        return new CurrentDateTimeProvider(timeUtil);
    }
}
