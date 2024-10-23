package com.example.demo.apidocs;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.helper.*;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.CafeTagEntity;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.CafeStudyTag;
import com.example.demo.implement.study.CafeStudyTagType;
import com.example.demo.implement.study.CafeTagType;
import com.example.demo.util.TimeUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public class CafeApiTest extends ApiDocsTest {

    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private CafeTagSaveHelper cafeTagSaveHelper;
    @Autowired
    private CafeCafeTagSaveHelper cafeCafeTagSaveHelper;
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
    @DisplayName("카페 상세정보 조회 API")
    void getCafeStudyDetail() throws Exception {
        CafeTagEntity cafeTag1 = cafeTagSaveHelper.saveCafeTag(CafeTagType.WIFI);
        CafeTagEntity cafeTag2 = cafeTagSaveHelper.saveCafeTag(CafeTagType.OUTLET);

        CafeEntity cafeEntity = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafeEntity, cafeTag1);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafeEntity, cafeTag2);

        menuSaveHelper.saveMenu("아메리카노", "1500", cafeEntity);
        menuSaveHelper.saveMenu("카페라떼", "3000", cafeEntity);

        Member member = memberSaveHelper.saveMember();

        LocalDateTime startDateTime = timeUtil.localDateTime(2000, 1, 1, 9, 0, 0);

        CafeStudyTag cafeStudyTag1 = cafeStudyTagSaveHelper.saveCafeStudyTag(CafeStudyTagType.DEVELOPMENT);
        CafeStudyTag cafeStudyTag2 = cafeStudyTagSaveHelper.saveCafeStudyTag(CafeStudyTagType.DESIGN);

        CafeStudy cafeStudy1 = cafeStudySaveHelper.saveCafeStudy(cafeEntity, member, startDateTime.plusHours(8), startDateTime.plusHours(10));
        cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy1, cafeStudyTag1);
        cafeStudySaveHelper.saveFinishedCafeStudy(cafeEntity, member, startDateTime.plusHours(2), startDateTime.plusHours(4));
        cafeStudySaveHelper.saveCafeStudy(cafeEntity, member, startDateTime.plusHours(5), startDateTime.plusHours(7));
        CafeStudy cafeStudy4 = cafeStudySaveHelper.saveFinishedCafeStudy(cafeEntity, member, startDateTime.plusHours(12), startDateTime.plusHours(14));
        cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy4, cafeStudyTag1);
        cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy4, cafeStudyTag2);

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
                        fieldWithPath("menusInfo[].price").description("메뉴 가격"),

                        fieldWithPath("openCafeStudiesInfo[].id").description("오픈된 카공 ID"),
                        fieldWithPath("openCafeStudiesInfo[].name").description("오픈된 카공 이름"),
                        fieldWithPath("openCafeStudiesInfo[].tags[]").description("오픈된 카공 태그 리스트"),
                        fieldWithPath("openCafeStudiesInfo[].startDateTime").description("오픈된 카공 시작 시간"),
                        fieldWithPath("openCafeStudiesInfo[].endDateTime").description("오픈된 카공 종료 시간"),
                        fieldWithPath("openCafeStudiesInfo[].maximumParticipants").description("오픈된 카공 최대 참가자 수"),
                        fieldWithPath("openCafeStudiesInfo[].currentParticipants").description("오픈된 카공 현재 참가자 수"),
                        fieldWithPath("openCafeStudiesInfo[].views").description("오픈된 카공 조회수"),
                        fieldWithPath("openCafeStudiesInfo[].memberComms").description("오픈된 카공 참여자 소통 여부"),
                        fieldWithPath("openCafeStudiesInfo[].recruitmentStatus").description("오픈된 카공 현재 모집 여부"),
                        fieldWithPath("openCafeStudiesInfo[].writer").description("오픈된 카공 작성자"),

                        fieldWithPath("closeCafeStudiesInfo[].id").description("닫힌 카공 ID"),
                        fieldWithPath("closeCafeStudiesInfo[].name").description("닫힌 카공 이름"),
                        fieldWithPath("closeCafeStudiesInfo[].tags[]").description("닫힌 카공 태그 리스트"),
                        fieldWithPath("closeCafeStudiesInfo[].startDateTime").description("닫힌 카공 시작 시간"),
                        fieldWithPath("closeCafeStudiesInfo[].endDateTime").description("닫힌 카공 종료 시간"),
                        fieldWithPath("closeCafeStudiesInfo[].maximumParticipants").description("닫힌 카공 최대 참가자 수"),
                        fieldWithPath("closeCafeStudiesInfo[].currentParticipants").description("닫힌 카공 현재 참가자 수"),
                        fieldWithPath("closeCafeStudiesInfo[].views").description("닫힌 카공 조회수"),
                        fieldWithPath("closeCafeStudiesInfo[].memberComms").description("닫힌 카공 참여자 소통 여부"),
                        fieldWithPath("closeCafeStudiesInfo[].recruitmentStatus").description("닫힌 카공 현재 모집 여부"),
                        fieldWithPath("closeCafeStudiesInfo[].writer").description("닫힌 카공 작성자")
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
