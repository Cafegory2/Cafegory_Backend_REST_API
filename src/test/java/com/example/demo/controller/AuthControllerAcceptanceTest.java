package com.example.demo.controller;

import com.example.demo.config.AcceptanceTest;
import com.example.demo.implement.token.JwtAccessToken;
import com.example.demo.implement.token.JwtToken;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("인증 관련 기능")
class AuthControllerAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("액세스 토큰을 재발급 받는다.")
    void refresh_accessToken() {
        //given
        JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();
        //when
        ExtractableResponse<Response> response =
            액세스_토큰_재발급_요청(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        //then
        JwtAccessToken accessToken = response.as(JwtAccessToken.class);

        assertThat(accessToken).isNotNull();
    }

    @Test
    @DisplayName("액세스 토큰 또는 리프레시 토큰이 존재하지 않으면 액세스 토큰을 재발급 받지 못한다.")
    void fail_refresh_accessToken() {
        //given
        JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();
        //when & then
        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + jwtToken.getAccessToken())
            .when()
            .post("/auth/refresh")
            .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value());

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Refresh-Token", jwtToken.getRefreshToken())
            .when()
            .post("/auth/refresh")
            .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    public static ExtractableResponse<Response> 액세스_토큰_재발급_요청(String accessToken, String refreshToken) {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken)
            .header("Refresh-Token", refreshToken)
            .when()
            .post("/auth/refresh")
            .then()
            .log().all()
            .extract();
    }
}
