package com.example.demo.config;

import com.example.demo.implement.member.Member;
import com.example.demo.implement.signup.SignupProcessor;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.implement.tokenmanagerment.JwtCafegoryTokenManager;
import com.example.demo.implement.tokenmanagerment.TokenClaims;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.service.login.LoginService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Component
public class MemberSignupAcceptanceTestHelper {

    @MockBean
    private LoginService mockLoginService;

    @Autowired
    private JwtCafegoryTokenManager jwtCafegoryTokenManager;
    @Autowired
    private SignupProcessor signupProcessor;
    @Autowired
    private MemberRepository memberRepository;

    public ExtractableResponse<Response> 회원_생성을_요청(String email, String nickname) {
        Long memberId = signupProcessor.signup(email, nickname);
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.changeProfileUrl("testProfileUrl");


        JwtToken jwtToken = jwtCafegoryTokenManager.createAccessAndRefreshToken(
            Map.of(TokenClaims.SUBJECT.getValue(), String.valueOf(memberId))
        );

        when(mockLoginService.socialLogin(any())).thenReturn(jwtToken);

        Map<String, String> params = new HashMap<>();
        params.put("code", "acceptanceKakaoCode");

        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParams(params)
            .when()
            .get("/login/kakao")
            .then()
            .log().all()
            .extract();
    }

    public void 회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public ExtractableResponse<Response> 회원_등록되어_있음(String email, String nickname) {
        return 회원_생성을_요청(email, nickname);
    }

    public JwtToken 로그인_되어_있음(String email, String nickname) {
        ExtractableResponse<Response> response = 회원_등록되어_있음(email, nickname);
        return response.as(JwtToken.class);
    }

}
