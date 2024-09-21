package com.example.demo.config;

import com.example.demo.implement.token.JwtToken;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class MemberSignupAcceptanceTestHelper {

    /**
     * 회원 생성을 요청하는 메서드.
     * <p>
     * 이 메서드를 사용할 때 이메일은 "test@gmail.com", 닉네임은 "testNickname"으로 고정되어 있습니다.
     * </p>
     * <p>
     * 자세한 내용은 SpyLoginService 클래스를 참고해 주세요.
     * </p>
     */
    public ExtractableResponse<Response> 회원_생성을_요청() {
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

    /**
     * 회원 생성을 요청하고 회원이 생성되어있는 메서드.
     * <p>
     * 이 메서드를 사용할 때 이메일은 "test@gmail.com", 닉네임은 "testNickname"으로 고정되어 있습니다.
     * </p>
     * <p>
     * 자세한 내용은 SpyLoginService 클래스를 참고해 주세요.
     * </p>
     */
    public void 회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public ExtractableResponse<Response> 회원_등록되어_있음() {
        return 회원_생성을_요청();
    }

    /**
     * 회원 생성을 요청하고 로그인이 되어있는 메서드.
     * <p>
     * 이 메서드를 사용할 때 이메일은 "test@gmail.com", 닉네임은 "testNickname"으로 고정되어 있습니다.
     * </p>
     * <p>
     * 자세한 내용은 SpyLoginService 클래스를 참고해 주세요.
     * </p>
     */
    public JwtToken 로그인_되어_있음() {
        ExtractableResponse<Response> response = 회원_등록되어_있음();
        return response.as(JwtToken.class);
    }
}
