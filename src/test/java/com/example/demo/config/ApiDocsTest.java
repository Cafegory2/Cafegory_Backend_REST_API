package com.example.demo.config;

import com.example.demo.helper.MemberSignupAcceptanceTestHelper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import({HelperConfig.class, SignupAcceptanceTestConfig.class})
@ExtendWith(RestDocumentationExtension.class)
public abstract class ApiDocsTest extends TestContainer{

    @LocalServerPort
    private int port;

    protected RequestSpecification spec;

    @Autowired
    private DatabaseCleanup databaseCleanup;
    @Autowired
    protected MemberSignupAcceptanceTestHelper memberSignupHelper;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider provider) {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
            databaseCleanup.afterPropertiesSet();
        }

        databaseCleanup.execute();

        this.spec = new RequestSpecBuilder().addFilter(documentationConfiguration(provider))
            .build();
    }
}
