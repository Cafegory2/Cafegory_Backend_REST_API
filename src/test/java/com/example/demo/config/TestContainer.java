package com.example.demo.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class TestContainer {

	protected static MySQLContainer mysqlContainer;

	static {
		mysqlContainer = new MySQLContainer<>("mysql:8.0");
		mysqlContainer.start();
	}

	@DynamicPropertySource
	protected static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mysqlContainer::getUsername);
		registry.add("spring.datasource.password", mysqlContainer::getPassword);
	}
}
