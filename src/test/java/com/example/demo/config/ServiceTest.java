package com.example.demo.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
@Import({HelperConfig.class})
public abstract class ServiceTest extends TestContainer {

	@Autowired
	private DatabaseCleanup databaseCleanup;

	@BeforeEach
	public void setUp() {
		databaseCleanup.execute();
	}
}
