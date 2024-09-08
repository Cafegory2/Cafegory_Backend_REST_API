package com.example.demo.apidocs;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.demo.controller.OAuthController;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.service.facade.OAuthFacade;
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

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(OAuthController.class)
public class OAuthControllerApiTest {

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private OAuthFacade oAuthFacade;

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
    void kakao() throws Exception {
        when(oAuthFacade.handleOauthLogin(any())).thenReturn(new JwtToken("access-token-value", "refresh-token-value"));

        this.mockMvc.perform(
                get("/oauth2/kakao?code={code}", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIU")
                    .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andDo(
                document("카카오 로그인",
                    resource(ResourceSnippetParameters.builder()
                        .description("로그인이 성공하면 JWT 액세스 토큰과 리프레시 토큰을 발급 받는다. https://kauth.kakao.com/oauth/authorize?client_id=dd715e41cd41949dc316c0243b964c44&redirect_uri=http:///oauth2/kakao&response_type=code")
                        .tag("OAuth")
                        .requestParameters(parameterWithName("code").optional()
                            .description("카카오 인증 코드"))
                        .responseFields(
                            fieldWithPath("accessToken").description("JWT 액세스 토큰"),
                            fieldWithPath("refreshToken").description("JWT 리프레시 토큰")
                        )
                        .build())));
    }
}