package com.example.demo.apidocs;

import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CafeTagPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static com.example.demo.persister.StudyTagPersister.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeTagEntity;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.helper.CafeCafeTagSaveHelper;
import com.example.demo.helper.CafeKeywordSaveHelper;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudyCafeStudyTagSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.CafeStudyTagSaveHelper;
import com.example.demo.helper.CafeTagSaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.domain.MemberComms;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;
import com.example.demo.util.TimeUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

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
    private MemberReader memberReader;
    @Autowired
    private TimeUtil timeUtil;

    // TODO: push 위해 주석 처리
    // @Test
    // void create() {
    // 	//given
    // 	CafeEntity cafe = cafeSaveHelper.saveCafeWith24For7();
    //
    // 	LocalDateTime startDateTime = timeUtil.now().plusHours(2);
    // 	LocalDateTime endDateTime = startDateTime.plusHours(1);
    //
    // 	Map<String, String> params = new HashMap<>();
    // 	params.put("name", "카페고리 스터디");
    // 	params.put("cafeId", String.valueOf(cafe.getId()));
    // 	params.put("startDateTime", startDateTime.toString());
    // 	params.put("endDateTime", endDateTime.toString());
    // 	params.put("memberComms", "WELCOME");
    // 	params.put("maxParticipants", String.valueOf(4));
    // 	params.put("introduction", "카페고리 스터디 소개글");
    //
    // 	JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();
    //
    // 	RestAssured.given(spec).log().all()
    // 		.filter(RestAssuredRestDocumentationWrapper.document(
    // 				"카공 생성 API",
    // 				requestHeaders(
    // 					headerWithName("Authorization").description("JWT 액세스 토큰"))
    // 			)
    // 		)
    // 		.contentType(ContentType.JSON)
    // 		.header("Authorization", "Bearer " + jwtToken.getAccessToken())
    // 		.body(params)
    // 		.when()
    // 		.post("/cafe-studies")
    // 		.then().log().all()
    // 		.statusCode(200);
    // }

    @Test
    void searchCafeStudies() {
        CafeTagEntity wifi = aCafeTag().withType(CafeTagType.WIFI).save();
        CafeTagEntity outlet = aCafeTag().withType(CafeTagType.OUTLET).save();
        CafeTagEntity comfortableSeating = aCafeTag().withType(CafeTagType.COMFORTABLE_SEATING).save();

        CafeEntity cafe1 = aCafe().includeKeywords("강남").includeTags(wifi, outlet).saveWith7daysFrom9To21();
        CafeEntity cafe2 = aCafe().includeKeywords("강남").includeTags(wifi, outlet, comfortableSeating).saveWith24For7();

        MemberEntity member = aMember().asCoordinator().save();

        CafeStudyTagEntity development = aTag().withType(CafeStudyTagType.DEVELOPMENT).save();
        CafeStudyTagEntity design = aTag().withType(CafeStudyTagType.DESIGN).save();

        aStudy().withCafe(cafe1).withMember(member).withMemberComms(MemberComms.WELCOME)
                .withStudyPeriod(
                        timeUtil.localDateTime(2000, 1, 1, 12, 0, 0),
                        timeUtil.localDateTime(2000, 1, 1, 14, 0, 0)
                )
                .includeTags(development)
                .save();
        aStudy().withCafe(cafe2).withMember(member).withMemberComms(MemberComms.WELCOME)
                .withStudyPeriod(
                        timeUtil.localDateTime(2000, 1, 1, 14, 0, 0),
                        timeUtil.localDateTime(2000, 1, 1, 16, 0, 0)
                )
                .includeTags(development, design)
                .save();

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

    // TODO: 로그인 되어 있음 메서드 수정
    // @Test
    // void deleteCafeStudy() {
    // 	LocalDateTime startDateTime = timeUtil.localDateTime(2000, 1, 1, 1, 0, 0);
    // 	LocalDateTime endDateTime = timeUtil.localDateTime(2000, 1, 1, 2, 0, 0);
    //
    // 	CafeEntity cafe = cafeSaveHelper.saveCafeWith24For7();
    // 	JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();
    // 	MemberEntity coordinator = memberReader.read("test@gmail.com");
    // 	CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, coordinator, startDateTime, endDateTime);
    //
    // 	RestAssured.given(spec).log().all()
    // 		.filter(RestAssuredRestDocumentationWrapper.document(
    // 			"카공 삭제 API",
    // 			requestHeaders(
    // 				headerWithName("Authorization").description("JWT 액세스 토큰")),
    // 			pathParameters(
    // 				parameterWithName("cafeStudyId").description("카공 ID")
    // 			))
    // 		)
    // 		.header("Authorization", "Bearer " + jwtToken.getAccessToken())
    // 		.contentType(ContentType.JSON)
    // 		.pathParam("cafeStudyId", cafeStudy.getId())
    // 		.when()
    // 		.delete("/cafe-studies/{cafeStudyId}")
    // 		.then().log().all()
    // 		.statusCode(200);
    // }
}
