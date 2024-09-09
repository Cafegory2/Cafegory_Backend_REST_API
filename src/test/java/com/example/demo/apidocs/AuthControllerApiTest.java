package com.example.demo.apidocs;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.demo.controller.AuthController;
import com.example.demo.implement.token.JwtAccessToken;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.service.token.JwtTokenManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(AuthController.class)
public class AuthControllerApiTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private JwtTokenManagementService jwtTokenManagementService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(MockMvcRestDocumentation
                .documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withResponseDefaults(Preprocessors.prettyPrint()))
            .build();
    }

    @Test
    void refresh() throws Exception {
        when(jwtTokenManagementService.verifyAndRefreshAccessToken(any(), any())).thenReturn(new JwtAccessToken("access-token-value"));

        JwtToken token = new JwtToken("Bearer existing-access-token", "existing-refresh-token");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(token);

        this.mockMvc.perform(
                post("/auth/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(
                document("액세스 토큰 재발급",
                    resource(ResourceSnippetParameters.builder()
                        .description("액세스 토큰을 재발급 받는다.")
                        .tag("Token")
                        .requestFields(
                            fieldWithPath("accessToken").description("JWT 액세스 토큰"),
                            fieldWithPath("refreshToken").description("JWT 리프레시 토큰, 리프레시 토큰 앞에는 Bearer을 붙이지 않는다.")
                        )
                        .responseFields(
                            fieldWithPath("accessToken").description("JWT 액세스 토큰")
                        )
                        .build())));
    }
}
