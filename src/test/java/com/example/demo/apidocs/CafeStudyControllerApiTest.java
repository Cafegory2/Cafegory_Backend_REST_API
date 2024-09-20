package com.example.demo.apidocs;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.util.TimeUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;

class CafeStudyControllerApiTest extends ApiDocsTest {

    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private TimeUtil timeUtil;

    @Test
    void create() {
        //given
        Cafe cafe = cafeSaveHelper.saveCafeWith24For7();

        Map<String, String> params = new HashMap<>();
        params.put("name", "카페고리 스터디");
        params.put("cafeId", String.valueOf(cafe.getId()));
        params.put("startDateTime", "2024-10-10T12:00:00");
        params.put("endDateTime", "2024-10-10T14:00:00");
        params.put("memberComms", "POSSIBLE");
        params.put("maxParticipants", String.valueOf(4));
        params.put("introduction", "카페고리 스터디 소개글");

        JwtToken jwtToken = memberSignupHelper.로그인_되어_있음("test@gmail.com", "testNickname");

        RestAssured.given(spec).log().all()
            .filter(RestAssuredRestDocumentationWrapper.document(
                    "카공 생성 API",
                    requestHeaders(
                        headerWithName("Authorization").description("JWT 액세스 토큰"))
                )
            )
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + jwtToken.getAccessToken())
            .body(params)
            .when()
            .post("/cafe-study/")
            .then().log().all()
            .statusCode(200);
    }
}
