package com.example.demo.qna.presentation;

import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.persister.CafeContextPersister;
import com.example.demo.persister.MemberPersister;
import com.example.demo.persister.StudyConextPersister;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.MemberComms;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.auth.implement.token.JwtToken;
import com.example.demo.util.TimeUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

class QnaApiTest extends ApiDocsTest {

    @Autowired
    private TimeUtil timeUtil;

    @Test
    @DisplayName("카공 Qna 댓글 등록 API")
    void leaveComment() {
        CafeEntity cafe = aCafe().persistWith24For7();
        MemberEntity coordinator = aMember().asCoordinator().persist();

        CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator)
                .withStudyPeriod(
                        timeUtil.localDateTime(2000, 1, 1, 12, 0, 0),
                        timeUtil.localDateTime(2000, 1, 1, 14, 0, 0)
                )
                .withMemberComms(MemberComms.WELCOME)
                .persist();

        Map<String, String> params = new HashMap<>();
        params.put("content", "댓글 내용");
        params.put("cafeStudyId", String.valueOf(study.getId()));

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