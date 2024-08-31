package com.example.demo.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class TestContainer {

	protected static PostgreSQLContainer postgreSQLContainer;

	static {
		postgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine");
		postgreSQLContainer.start();
	}

	@DynamicPropertySource
	protected static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}
}
