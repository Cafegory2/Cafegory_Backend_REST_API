package com.example.demo.apidocs;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

public class LoginControllerApiTest extends ApiDocsTest {

    @Test
    void refresh() {
        RestAssured.given(spec).log().all()
            .filter(RestAssuredRestDocumentationWrapper.document(
                    "카카오 로그인 API",
                    requestParameters(
                        parameterWithName("code").description("카카오 인증 code")
                    ),
                    responseFields(
                        fieldWithPath("accessToken").description("JWT 액세스 토큰"),
                        fieldWithPath("refreshToken").description("JWT 리프레시 토큰")
                    )
                )
            )
            .contentType(ContentType.JSON)
            .param("code", "kakaoCode")
            .when()
            .get("/login/kakao")
            .then().log().all()
            .statusCode(200);
    }
}
