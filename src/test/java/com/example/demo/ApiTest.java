package com.example.demo;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.example.demo.controller.swaggertest.ApiTestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;

@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(ApiTestController.class)
public class ApiTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withResponseDefaults(Preprocessors.prettyPrint())
                )
                .build();
    }

    @Test
    void documentHelloEndpoint() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/hello?name={name}", "JDriven")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(
                        document("hello-controller",
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .summary("Greets a user.")
                                                .description("test")
                                                .responseSchema(Schema.schema("get-hello-response"))
                                                .tag("Greeting")
                                                .requestParameters(
                                                        parameterWithName("name").optional()
                                                                .description("Name of the User.")
                                                )
                                                .build()
                                ))
                );

    }


}
