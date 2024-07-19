package com.example.demo.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;

public abstract class TestContainer {

	private static final MariaDBContainer mariadbContainer;

	static {
		mariadbContainer = new MariaDBContainer("mariadb:10.11");
		mariadbContainer.start();
	}

	@DynamicPropertySource
	private static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mariadbContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mariadbContainer::getUsername);
		registry.add("spring.datasource.password", mariadbContainer::getPassword);
	}
}
