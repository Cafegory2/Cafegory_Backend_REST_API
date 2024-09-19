package com.example.demo.apidocs;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.demo.controller.ProfileController;
import com.example.demo.dto.profile.WelcomeProfileResponse;
import com.example.demo.factory.TestJwtFactory;
import com.example.demo.service.profile.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.example.demo.factory.TestJwtFactory.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(ProfileController.class)
public class ProfileControllerApiTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

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
    @WithMockUser(username = "1")
    void welcome() throws Exception {
        when(profileService.getWelcomeProfile(1L))
            .thenReturn(new WelcomeProfileResponse("테스트닉네임", "testProfileUrl"));

        this.mockMvc.perform(
                get("/profile/welcome")
                    .header("Authorization", "Bearer existing-access-token")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(
                document("회원가입 환영 페이지 API",
                    resource(ResourceSnippetParameters.builder()
                        .description("회원가입 환영 페이지에 알맞는 응답 데이터를 받는다.")
                        .tag("Profile")
                        .requestHeaders(
                            headerWithName("Authorization").description("JWT 액세스 토큰")
                        )
                        .responseFields(
                            fieldWithPath("nickname").description("회원 닉네임"),
                            fieldWithPath("profileUrl").description("회원 프로필 이미지 url")
                        )
                        .build())));
    }
}
