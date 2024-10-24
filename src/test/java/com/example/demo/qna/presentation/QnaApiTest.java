package com.example.demo.qna.presentation;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.helper.*;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.MemberComms;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

class QnaApiTest extends ApiDocsTest {

    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private CafeStudySaveHelper cafeStudySaveHelper;
    @Autowired
    private MemberSaveHelper memberSaveHelper;
    @Autowired
    private TimeUtil timeUtil;

    @Test
    @DisplayName("카공 Qna 댓글 등록 API")
    void leaveComment() {
        CafeEntity cafe = cafeSaveHelper.saveCafeWith24For7();

        MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");

        LocalDateTime startDateTime1 = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);
        CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudyWithMemberComms(cafe, coordinator,
            startDateTime1.plusHours(2), startDateTime1.plusHours(4), MemberComms.WELCOME);

        Map<String, String> params = new HashMap<>();
        params.put("content", "댓글 내용");
        params.put("cafeStudyId", String.valueOf(cafeStudy.getId()));

        JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();

        RestAssured.given(spec).log().all()
            .filter(RestAssuredRestDocumentationWrapper.document(
                    "카페 Qna 댓글 등록 API",
                    requestHeaders(
                        headerWithName("Authorization").description("JWT 액세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("content").description("댓글 내용"),
                        fieldWithPath("cafeStudyId").description("카공 ID")
                    ),
                responseFields(
                    fieldWithPath("commentInfo.id").description("댓글 ID"),
                    fieldWithPath("commentInfo.content").description("댓글 내용"),
                    fieldWithPath("commentInfo.createdDate").description("댓글 작성일"),
                    fieldWithPath("writerInfo.id").description("작성자 ID"),
                    fieldWithPath("writerInfo.nickname").description("작성자 닉네임")
                )
                )
            )
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + jwtToken.getAccessToken())
            .body(params)
            .when()
            .post("/qna/comments")
            .then().log().all()
            .statusCode(200);
    }
}