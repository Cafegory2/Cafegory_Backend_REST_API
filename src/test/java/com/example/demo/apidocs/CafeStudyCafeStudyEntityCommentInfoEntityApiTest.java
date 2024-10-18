package com.example.demo.apidocs;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.helper.*;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.util.TimeUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

public class CafeStudyCafeStudyEntityCommentInfoEntityApiTest extends ApiDocsTest {

    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private CafeStudySaveHelper cafeStudySaveHelper;
    @Autowired
    private MemberSaveHelper memberSaveHelper;
    @Autowired
    private TimeUtil timeUtil;

    @Test
    @DisplayName("카공 Q&A 댓글 생성 API")
    void saveComment() {
        Cafe cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();

        Member coordinator = memberSaveHelper.saveMember("test1@gmail.com", "카공장");

        LocalDateTime startDateTime = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);
        CafeStudyEntity cafeStudyEntity = cafeStudySaveHelper.saveCafeStudy(cafe, coordinator,
            startDateTime, startDateTime.plusHours(2));

        JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();

        Map<String, String> params = new HashMap<>();
        params.put("content", "댓글 내용");
        params.put("cafeStudyId", String.valueOf(cafeStudyEntity.getId()));

        RestAssured.given(spec).log().all()
            .filter(RestAssuredRestDocumentationWrapper.document(
                    "카공 Q&A 댓글 생성 API",
                    requestHeaders(
                        headerWithName("Authorization").description("JWT 액세스 토큰")),
                    requestFields(
                        fieldWithPath("cafeStudyId").description("카공 ID"),
                        fieldWithPath("content").description("댓글 내용")
                    )
                )
            )
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + jwtToken.getAccessToken())
            .body(params)
            .when()
            .post("/cafe-studies")
            .then().log().all()
            .statusCode(200);
    }
}
