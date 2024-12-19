package com.example.demo.apidocs;

import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CafeTagPersister.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeTagEntity;
import com.example.demo.cafe.infrastructure.ReviewEntity;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.helper.CafeCafeTagSaveHelper;
import com.example.demo.helper.CafeKeywordSaveHelper;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudyCafeStudyTagSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.CafeStudyTagSaveHelper;
import com.example.demo.helper.CafeTagSaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.helper.ReviewCafeTagSaveHelper;
import com.example.demo.helper.ReviewSaveHelper;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.trash.implement.token.JwtToken;
import com.example.demo.util.TimeUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ProfileControllerApiTest extends ApiDocsTest {

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
	private ReviewSaveHelper reviewSaveHelper;
	@Autowired
	private ReviewCafeTagSaveHelper reviewCafeTagSaveHelper;
	@Autowired
	private MemberReader memberReader;

	@Autowired
	private TimeUtil timeUtil;

	@Test
	void welcome() {
		JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();

		RestAssured.given(spec).log().all()
			.filter(RestAssuredRestDocumentationWrapper.document(
					"회원가입 환영 페이지 API",
					requestHeaders(
						headerWithName("Authorization").description("JWT 액세스 토큰")
					),
					responseFields(
						fieldWithPath("nickname").description("회원 닉네임"),
						fieldWithPath("profileUrl").description("회원 프로필 URL")
					)
				)
			)
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + jwtToken.getAccessToken())
			.when()
			.get("/profile/welcome")
			.then().log().all()
			.statusCode(200);
	}

	@Test
	@DisplayName("마이페이지 조회 API")
	void mypage() {
		CafeEntity cafe1 = aCafe().saveWith24For7();
		CafeEntity cafe2 = aCafe().saveWith24For7();

		JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();
		MemberEntity member = memberReader.read("test@gmail.com");

		CafeTagEntity wifi = aCafeTag().withType(CafeTagType.WIFI).save();
		CafeTagEntity outlet = aCafeTag().withType(CafeTagType.OUTLET).save();

		ReviewEntity review1 = reviewSaveHelper.saveReview(cafe1, member);
		reviewCafeTagSaveHelper.saveReview(review1, wifi);
		ReviewEntity review2 = reviewSaveHelper.saveReview(cafe2, member);
		reviewCafeTagSaveHelper.saveReview(review2, wifi);
		reviewCafeTagSaveHelper.saveReview(review2, outlet);

		RestAssured.given(spec).log().all()
			.filter(RestAssuredRestDocumentationWrapper.document(
					"마이 페이지 조회 API",
					requestHeaders(
						headerWithName("Authorization").description("JWT 액세스 토큰")
					)
				)
			)
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + jwtToken.getAccessToken())
			.when()
			.get("/profile/mypage")
			.then().log().all()
			.statusCode(200);
	}
}
