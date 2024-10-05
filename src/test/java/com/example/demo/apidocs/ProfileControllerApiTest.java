package com.example.demo.apidocs;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.implement.token.JwtToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class ProfileControllerApiTest extends ApiDocsTest {

    @Test
    void welcome() {
        JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();

        RestAssured.given(spec).log().all()
            .filter(RestAssuredRestDocumentationWrapper.document(
                    "회원가입 환영 페이지 API",
                    requestHeaders(
                        headerWithName("Authorization").description("JWT 액세스 토큰")
                    ),
                    responseFields(
                        fieldWithPath("nickname").description("회원 닉네임"),
                        fieldWithPath("profileUrl").description("회원 프로필 URL")
                    )
                )
            )
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + jwtToken.getAccessToken())
            .when()
            .get("/profile/welcome")
            .then().log().all()
            .statusCode(200);
    }
}
