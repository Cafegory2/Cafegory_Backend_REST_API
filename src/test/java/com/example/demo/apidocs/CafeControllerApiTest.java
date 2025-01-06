package com.example.demo.apidocs;

import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CafeTagPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static com.example.demo.persister.StudyTagPersister.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.db.cafe.cafe.CafeEntity;
import com.example.demo.db.cafe.tag.CafeTagEntity;
import com.example.demo.db.member.MemberEntity;
import com.example.demo.db.study.tag.CafeStudyTagEntity;
import com.example.demo.domain.cafe.domain.CafeTagType;
import com.example.demo.domain.study.domain.CafeStudyTagType;
import com.example.demo.domain.study.domain.RecruitmentStatus;
import com.example.demo.time.TimeUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CafeControllerApiTest extends ApiDocsTest {

	@Autowired
	private TimeUtil timeUtil;

	@Test
	@DisplayName("카페 상세정보 조회 API")
	void getCafeStudyDetail() throws Exception {
		CafeTagEntity wifi = aCafeTag().withType(CafeTagType.WIFI).persist();
		CafeTagEntity outlet = aCafeTag().withType(CafeTagType.OUTLET).persist();

		CafeEntity cafeEntity = aCafe()
			.includeTags(wifi, outlet)
			.includeMenu("아메리카노", "1500")
			.includeMenu("카페라떼", "3000")
			.persistWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().persist();

		CafeStudyTagEntity development = aTag().withType(CafeStudyTagType.DEVELOPMENT).persist();
		CafeStudyTagEntity design = aTag().withType(CafeStudyTagType.DESIGN).persist();

		aStudy().withStudyPeriod(
				timeUtil.localDateTime(2000, 1, 1, 17, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 19, 0, 0)
			)
			.withRecruitmentStatus(RecruitmentStatus.OPEN)
			.withCafe(cafeEntity)
			.withMember(coordinator)
			.includeTags(development)
			.persist();

		aStudy().withStudyPeriod(
				timeUtil.localDateTime(2000, 1, 1, 11, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 13, 0, 0)
			)
			.withRecruitmentStatus(RecruitmentStatus.CLOSED)
			.withCafe(cafeEntity)
			.withMember(coordinator)
			.persist();

		aStudy().withStudyPeriod(
				timeUtil.localDateTime(2000, 1, 1, 13, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 15, 0, 0)
			)
			.withRecruitmentStatus(RecruitmentStatus.OPEN)
			.withCafe(cafeEntity)
			.withMember(coordinator)
			.persist();

		aStudy().withStudyPeriod(
				timeUtil.localDateTime(2000, 1, 1, 21, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 23, 0, 0)
			)
			.withRecruitmentStatus(RecruitmentStatus.CLOSED)
			.withCafe(cafeEntity)
			.withMember(coordinator)
			.includeTags(development, design)
			.persist();

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

						// fieldWithPath("openCafeStudiesInfo[].id").description("오픈된 카공 ID"),
						// fieldWithPath("openCafeStudiesInfo[].name").description("오픈된 카공 이름"),
						// fieldWithPath("openCafeStudiesInfo[].tags[]").description("오픈된 카공 태그 리스트"),
						// fieldWithPath("openCafeStudiesInfo[].startDateTime").description("오픈된 카공 시작 시간"),
						// fieldWithPath("openCafeStudiesInfo[].endDateTime").description("오픈된 카공 종료 시간"),
						// fieldWithPath("openCafeStudiesInfo[].maximumParticipants").description("오픈된 카공 최대 참가자 수"),
						// fieldWithPath("openCafeStudiesInfo[].currentParticipants").description("오픈된 카공 현재 참가자 수"),
						// fieldWithPath("openCafeStudiesInfo[].views").description("오픈된 카공 조회수"),
						// fieldWithPath("openCafeStudiesInfo[].memberComms").description("오픈된 카공 참여자 소통 여부"),
						// fieldWithPath("openCafeStudiesInfo[].recruitmentStatus").description("오픈된 카공 현재 모집 여부"),
						// fieldWithPath("openCafeStudiesInfo[].writer").description("오픈된 카공 작성자"),
						//
						// fieldWithPath("closeCafeStudiesInfo[].id").description("닫힌 카공 ID"),
						// fieldWithPath("closeCafeStudiesInfo[].name").description("닫힌 카공 이름"),
						// fieldWithPath("closeCafeStudiesInfo[].tags[]").description("닫힌 카공 태그 리스트"),
						// fieldWithPath("closeCafeStudiesInfo[].startDateTime").description("닫힌 카공 시작 시간"),
						// fieldWithPath("closeCafeStudiesInfo[].endDateTime").description("닫힌 카공 종료 시간"),
						// fieldWithPath("closeCafeStudiesInfo[].maximumParticipants").description("닫힌 카공 최대 참가자 수"),
						// fieldWithPath("closeCafeStudiesInfo[].currentParticipants").description("닫힌 카공 현재 참가자 수"),
						// fieldWithPath("closeCafeStudiesInfo[].views").description("닫힌 카공 조회수"),
						// fieldWithPath("closeCafeStudiesInfo[].memberComms").description("닫힌 카공 참여자 소통 여부"),
						// fieldWithPath("closeCafeStudiesInfo[].recruitmentStatus").description("닫힌 카공 현재 모집 여부"),
						// fieldWithPath("closeCafeStudiesInfo[].writer").description("닫힌 카공 작성자")
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
