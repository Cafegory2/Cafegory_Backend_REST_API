package com.example.demo.apidocs;

import static org.mockito.ArgumentMatchers.any;

//@AutoConfigureMockMvc
//@ExtendWith(RestDocumentationExtension.class)
//@WebMvcTest(LoginController.class)
public class MockMvcReference {

    //mockMvc로 테스트 작성하는 방법


//    @Autowired
//    private WebApplicationContext context;
//    @MockBean
//    private LoginServiceImpl loginServiceImpl;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp(RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//            .apply(MockMvcRestDocumentation
//                .documentationConfiguration(restDocumentation)
//                .operationPreprocessors()
//                .withResponseDefaults(Preprocessors.prettyPrint()))
//            .build();
//    }
//
//    @Test
//    void kakao() throws Exception {
//        when(loginServiceImpl.socialLogin(any())).thenReturn(new JwtToken("access-token-value", "refresh-token-value"));
//
//        this.mockMvc.perform(
//                get("/login/kakao?code={code}", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIU")
//                    .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//            .andDo(
//                document("카카오 로그인",
//                    resource(ResourceSnippetParameters.builder()
//                        .description("로그인이 성공하면 JWT 액세스 토큰과 리프레시 토큰을 발급 받는다. https://kauth.kakao.com/oauth/authorize?client_id=dd715e41cd41949dc316c0243b964c44&redirect_uri=http://{domain}/login/kakao&response_type=code")
//                        .tag("Login")
//                        .requestParameters(parameterWithName("code").description("카카오 인증 코드"))
//                        .responseFields(
//                            fieldWithPath("accessToken").description("JWT 액세스 토큰"),
//                            fieldWithPath("refreshToken").description("JWT 리프레시 토큰")
//                        )
//                        .build())));
//    }
}
