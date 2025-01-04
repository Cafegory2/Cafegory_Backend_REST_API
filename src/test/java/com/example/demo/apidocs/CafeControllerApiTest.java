package com.example.demo.apidocs;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.junit.jupiter.api.DisplayName;
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
import com.example.demo.helper.MenuSaveHelper;
import com.example.demo.util.TimeUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CafeControllerApiTest extends ApiDocsTest {

	@Autowired
	private CafeSaveHelper cafeSaveHelper;
	@Autowired
	private CafeTagSaveHelper cafeTagSaveHelper;
	@Autowired
	private CafeCafeTagSaveHelper cafeCafeTagSaveHelper;
	@Autowired
	private CafeKeywordSaveHelper cafeKeywordSaveHelper;
	@Autowired
	private MenuSaveHelper menuSaveHelper;
	@Autowired
	private CafeStudySaveHelper cafeStudySaveHelper;
	@Autowired
	private MemberSaveHelper memberSaveHelper;
	@Autowired
	private CafeStudyTagSaveHelper cafeStudyTagSaveHelper;
	@Autowired
	private CafeStudyCafeStudyTagSaveHelper cafeStudyCafeStudyTagSaveHelper;
	@Autowired
	private TimeUtil timeUtil;

	@Test
	void searchCafes() {
		CafeTagEntity cafeTag1 = cafeTagSaveHelper.saveCafeTag(CafeTagType.WIFI);
		CafeTagEntity cafeTag2 = cafeTagSaveHelper.saveCafeTag(CafeTagType.OUTLET);
		CafeTagEntity cafeTag3 = cafeTagSaveHelper.saveCafeTag(CafeTagType.COMFORTABLE_SEATING);

		CafeEntity cafe1 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
		cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe1);
		cafeCafeTagSaveHelper.saveCafeCafeTag(cafe1, cafeTag1);
		cafeCafeTagSaveHelper.saveCafeCafeTag(cafe1, cafeTag2);
		CafeEntity cafe2 = cafeSaveHelper.saveCafeWith24For7();
		cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe2);
		cafeCafeTagSaveHelper.saveCafeCafeTag(cafe2, cafeTag1);
		cafeCafeTagSaveHelper.saveCafeCafeTag(cafe2, cafeTag2);
		cafeCafeTagSaveHelper.saveCafeCafeTag(cafe2, cafeTag3);

		// note: String type이 아니면 오류 발생...
		// LocalDateTime openingDateTime = timeUtil.localDateTime(2001, 1, 1, 10, 0, 0);
		// LocalDateTime closingDateTime = timeUtil.localDateTime(2001, 1, 1, 23, 59, 59);

		String openingDateTime = "2000-01-01T10:00:00";
		String closingDateTime = "2000-01-01T20:00:00";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("keyword", "강남");
		params.add("openingDateTime", openingDateTime);
		params.add("closingDateTime", closingDateTime);
		params.add("cafeTags", CafeTagType.WIFI.toString());
		params.add("cafeTags", CafeTagType.OUTLET.toString());
		params.add("page", "0");
		params.add("sizePerPage", "10");

		RestAssured.given(spec).log().all()
			.filter(RestAssuredRestDocumentationWrapper.document(
					"카페 목록 조회 API",
					requestParameters(
						parameterWithName("keyword").description("검색어"),
						parameterWithName("openingDateTime").description("필터링 오픈시간"),
						parameterWithName("closingDateTime").description("필터링 마감시간"),
						parameterWithName("cafeTags").description("카페 태그 리스트, 여러개의 카페 태그를 넣을 수 있다."),
						parameterWithName("page").description("페이지 번호, 0부터 시작한다."),
						parameterWithName("sizePerPage").description("한 페이지에 들어가는 컨텐츠 개수")
					)
				)
			)
			.contentType(ContentType.JSON)
			.params(params)
			.when()
			.get("/cafes")
			.then().log().all()
			.statusCode(200);
	}

	@Test
	@DisplayName("카페 상세정보 조회 API")
	void getCafeDetail() throws Exception {
		CafeTagEntity cafeTag1 = cafeTagSaveHelper.saveCafeTag(CafeTagType.WIFI);
		CafeTagEntity cafeTag2 = cafeTagSaveHelper.saveCafeTag(CafeTagType.OUTLET);

		CafeEntity cafeEntity = cafeSaveHelper.saveCafeWith7daysFrom9To21();
		cafeCafeTagSaveHelper.saveCafeCafeTag(cafeEntity, cafeTag1);
		cafeCafeTagSaveHelper.saveCafeCafeTag(cafeEntity, cafeTag2);

		menuSaveHelper.saveMenu("아메리카노", "1500", cafeEntity);
		menuSaveHelper.saveMenu("카페라떼", "3000", cafeEntity);
		//
		// MemberEntity member = memberSaveHelper.saveMember();
		//
		// LocalDateTime startDateTime = timeUtil.localDateTime(2000, 1, 1, 9, 0, 0);
		//
		// CafeStudyTagEntity cafeStudyTag1 = cafeStudyTagSaveHelper.saveCafeStudyTag(CafeStudyTagType.DEVELOPMENT);
		// CafeStudyTagEntity cafeStudyTag2 = cafeStudyTagSaveHelper.saveCafeStudyTag(CafeStudyTagType.DESIGN);
		//
		// CafeStudyEntity cafeStudy1 = cafeStudySaveHelper.saveCafeStudy(cafeEntity, member, startDateTime.plusHours(8),
		// 	startDateTime.plusHours(10));
		// cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy1, cafeStudyTag1);
		// cafeStudySaveHelper.saveFinishedCafeStudy(cafeEntity, member, startDateTime.plusHours(2),
		// 	startDateTime.plusHours(4));
		// cafeStudySaveHelper.saveCafeStudy(cafeEntity, member, startDateTime.plusHours(5), startDateTime.plusHours(7));
		// CafeStudyEntity cafeStudy4 = cafeStudySaveHelper.saveFinishedCafeStudy(cafeEntity, member,
		// 	startDateTime.plusHours(12), startDateTime.plusHours(14));
		// cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy4, cafeStudyTag1);
		// cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy4, cafeStudyTag2);

		RestAssured.given(spec).log().all()
			.filter(RestAssuredRestDocumentationWrapper.document(
					"카페 상세정보 조회 API",
					pathParameters(
						parameterWithName("cafeId").description("카페 ID")
					),
					responseFields(
						fieldWithPath("cafeInfo.id").description("카페 ID"),
						fieldWithPath("cafeInfo.name").description("카페 이름"),
						fieldWithPath("cafeInfo.imgUrl").description("카페 이미지 URL"),
						fieldWithPath("cafeInfo.address").description("카페 주소"),
						fieldWithPath("cafeInfo.openingTime").description("카페 영업 시작 시간"),
						fieldWithPath("cafeInfo.closingTime").description("카페 영업 종료 시간"),
						fieldWithPath("cafeInfo.open").description("카페 영업 여부"),
						fieldWithPath("cafeInfo.sns").description("카페 SNS 링크"),
						fieldWithPath("cafeInfo.tags[]").description("카페에 해당하는 태그 리스트"),

						fieldWithPath("menusInfo[].name").description("메뉴 이름"),
						fieldWithPath("menusInfo[].price").description("메뉴 가격")
					)
				)
			)
			.contentType(ContentType.JSON)
			.when()
			.get("/cafes/{cafeId}", cafeEntity.getId())
			.then().log().all()
			.statusCode(200);
	}
}
