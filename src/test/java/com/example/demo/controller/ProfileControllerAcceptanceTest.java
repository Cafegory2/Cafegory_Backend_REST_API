package com.example.demo.controller;

import com.example.demo.config.AcceptanceTest;
import com.example.demo.dto.profile.WelcomeProfileResponse;
import com.example.demo.implement.token.JwtToken;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.*;


@DisplayName("프로필 관련 기능")
@Slf4j
class ProfileControllerAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("회원가입 환영 페이지에 알맞는 데이터를 조회한다.")
    void welcome() {
        JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();

        ExtractableResponse<Response> welcomeResponse = 회원가입_환영_페이지_데이터_조회_요청(jwtToken.getAccessToken());

        회원가입_환영_페이지_데이터_조회됨(welcomeResponse);
    }

    public static ExtractableResponse<Response> 회원가입_환영_페이지_데이터_조회_요청(String accessToken) {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .get("/profile/welcome")
            .then()
            .log().all()
            .extract();
    }

    public static void 회원가입_환영_페이지_데이터_조회됨(ExtractableResponse<Response> response) {
        WelcomeProfileResponse welcomeProfileResponse = response.as(WelcomeProfileResponse.class);

        assertThat(welcomeProfileResponse.getNickname()).isEqualTo("testNickname");
        assertThat(welcomeProfileResponse.getProfileUrl()).isNotNull();
    }
}
