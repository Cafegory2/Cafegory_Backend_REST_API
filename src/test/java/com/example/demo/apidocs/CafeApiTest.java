package com.example.demo.apidocs;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.dto.cafe.CafeDetailResponse;
import com.example.demo.helper.CafeCafeTagSaveHelper;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeTagSaveHelper;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.cafe.CafeTag;
import com.example.demo.implement.study.CafeTagType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public class CafeApiTest extends ApiDocsTest {

    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private CafeTagSaveHelper cafeTagSaveHelper;
    @Autowired
    private CafeCafeTagSaveHelper cafeCafeTagSaveHelper;

    @Test
    @DisplayName("카페 상세정보 조회 API")
    void getCafeStudyDetail() {
        CafeTag cafeTag1 = cafeTagSaveHelper.saveCafeTag(CafeTagType.WIFI);
        CafeTag cafeTag2 = cafeTagSaveHelper.saveCafeTag(CafeTagType.OUTLET);

        Cafe cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe, cafeTag1);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe, cafeTag2);

        //TODO 응답 스펙 검증하기
        CafeDetailResponse response = RestAssured.given(spec).log().all()
            .filter(RestAssuredRestDocumentationWrapper.document(
                    "카페 상세정보 조회 API",
                    pathParameters(
                        parameterWithName("cafeId").description("카페 ID")
                    )
                )
            )
            .contentType(ContentType.JSON)
            .when()
            .get("/cafe/{cafeId}", cafe.getId())
            .then().log().all()
            .statusCode(200)
            .extract().as(CafeDetailResponse.class);
    }
}
