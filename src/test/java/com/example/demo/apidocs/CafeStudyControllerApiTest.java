package com.example.demo.apidocs;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.helper.*;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.cafe.CafeTag;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.*;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.util.TimeUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
    private CafeTagSaveHelper cafeTagSaveHelper;
    @Autowired
    private CafeCafeTagSaveHelper cafeCafeTagSaveHelper;
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
            .post("/cafe-studies")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    void searchCafeStudies() {
        CafeTag cafeTag1 = cafeTagSaveHelper.saveCafeTag(CafeTagType.WIFI);
        CafeTag cafeTag2 = cafeTagSaveHelper.saveCafeTag(CafeTagType.OUTLET);
        CafeTag cafeTag3 = cafeTagSaveHelper.saveCafeTag(CafeTagType.COMFORTABLE_SEATING);

        Cafe cafe1 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe1);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe1, cafeTag1);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe1, cafeTag2);
        Cafe cafe2 = cafeSaveHelper.saveCafeWith24For7();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe2);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe2, cafeTag1);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe2, cafeTag2);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe2, cafeTag3);

        Member member = memberSaveHelper.saveMember("cafegory@gmail.com");

        CafeStudyTag cafeStudyTag1 = cafeStudyTagSaveHelper.saveCafeStudyTag(CafeStudyTagType.DEVELOPMENT);
        CafeStudyTag cafeStudyTag2 = cafeStudyTagSaveHelper.saveCafeStudyTag(CafeStudyTagType.DESIGN);

        LocalDateTime startDateTime1 = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);

        CafeStudy cafeStudy1 = cafeStudySaveHelper.saveCafeStudyWithMemberComms(cafe1, member,
            startDateTime1.plusHours(2), startDateTime1.plusHours(4), MemberComms.WELCOME);
        cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy1, cafeStudyTag1);
        CafeStudy cafeStudy2 = cafeStudySaveHelper.saveCafeStudyWithMemberComms(cafe2, member,
            startDateTime1.plusHours(4), startDateTime1.plusHours(6), MemberComms.WELCOME);
        cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy2, cafeStudyTag1);
        cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy2, cafeStudyTag2);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "강남");
        params.add("date", "2000-01-01");
        params.add("cafeStudyTag", CafeStudyTagType.DEVELOPMENT.toString());
        params.add("cafeTags", CafeTagType.WIFI.toString());
        params.add("cafeTags", CafeTagType.OUTLET.toString());
        params.add("memberComms", MemberComms.WELCOME.toString());
        params.add("page", "0");
        params.add("sizePerPage", "10");

        RestAssured.given(spec).log().all()
            .filter(RestAssuredRestDocumentationWrapper.document(
                    "카공 목록 조회 API",
                    requestParameters(
                        parameterWithName("keyword").description("검색어"),
                        parameterWithName("date").description("필터링 날짜"),
                        parameterWithName("cafeStudyTag").description("카공 태그"),
                        parameterWithName("cafeTags").description("카페 태그 리스트, 여러개의 카공 태그를 넣을 수 있다."),
                        parameterWithName("memberComms").description("소통여부"),
                        parameterWithName("page").description("페이지 번호, 0부터 시작한다."),
                        parameterWithName("sizePerPage").description("한 페이지에 들어가는 컨텐츠 개수")
                    )
                )
            )
            .contentType(ContentType.JSON)
            .params(params)
            .when()
            .get("/cafe-studies")
            .then().log().all()
            .statusCode(200);
    }
}
