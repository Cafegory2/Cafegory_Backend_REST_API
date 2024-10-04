package com.example.demo.apidocs;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.helper.*;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.CafeStudyTag;
import com.example.demo.implement.study.CafeStudyTagType;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.util.TimeUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

class CafeStudyControllerApiTest extends ApiDocsTest {

    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private CafeKeywordSaveHelper cafeKeywordSaveHelper;
    @Autowired
    private CafeStudyTagSaveHelper cafeStudyTagSaveHelper;
    @Autowired
    private CafeStudySaveHelper cafeStudySaveHelper;
    @Autowired
    private CafeStudyCafeStudyTagSaveHelper cafeStudyCafeStudyTagSaveHelper;
    @Autowired
    private MemberSaveHelper memberSaveHelper;
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
        params.put("memberComms", "WELCOME");
        params.put("maxParticipants", String.valueOf(4));
        params.put("introduction", "카페고리 스터디 소개글");

        JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();

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
            .post("/cafe-study")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    void searchCafeStudies() {
        Cafe cafe1 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe1);

        Member member = memberSaveHelper.saveMember("cafegory@gmail.com");

        CafeStudyTag cafeStudyTag1 = cafeStudyTagSaveHelper.saveCafeStudyTag(CafeStudyTagType.DEVELOPMENT);

        LocalDateTime startDateTime = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);

        CafeStudy cafeStudy1 = cafeStudySaveHelper.saveCafeStudy(cafe1, member,
            startDateTime.plusHours(2), startDateTime.plusHours(4));
        cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy1, cafeStudyTag1);

        Map<String, String> params = new HashMap<>();
        params.put("keyword", "강남");
        params.put("page", "0");
        params.put("sizePerPage", "10");

        RestAssured.given(spec).log().all()
            .filter(RestAssuredRestDocumentationWrapper.document(
                    "카공 목록 조회 API",
                    requestParameters(
                        parameterWithName("keyword").description("검색어"),
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("sizePerPage").description("페이지 당 개수")

                    )
                )
            )
            .contentType(ContentType.JSON)
            .params(params)
            .when()
//            .get("/cafe-study/list?keyword=aa&page=1&sizePerPage=10")
            .get("/cafe-study/list")
            .then().log().all()
            .statusCode(200);
    }
}
