package com.example.demo.apidocs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.junit.jupiter.api.Test;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.auth.implement.token.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AuthControllerApiTest extends ApiDocsTest {

	@Test
	void refresh() {
		JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();

		RestAssured.given(spec).log().all()
			.filter(RestAssuredRestDocumentationWrapper.document(
				"액세스 토큰 재발급 API",
				requestHeaders(
					headerWithName("Authorization").description("JWT 액세스 토큰"),
					headerWithName("Refresh-Token").description("JWT 리프레시 토큰, 리프레시 토큰 앞에는 Bearer을 붙이지 않는다.")
				),
				responseFields(
					fieldWithPath("accessToken").description("JWT 액세스 토큰")
				)
			))
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + jwtToken.getAccessToken())
			.header("Refresh-Token", jwtToken.getRefreshToken())
			.when()
			.post("/auth/refresh")
			.then().log().all()
			.statusCode(200);
	}
}
