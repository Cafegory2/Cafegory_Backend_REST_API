package com.example.demo.apidocs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import com.example.demo.helper.*;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.implement.cafe.CafeTagEntity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.cafe.infrastructure.ReviewEntity;
import com.example.demo.implement.study.CafeTagType;
import com.example.demo.util.TimeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.implement.token.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

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
		CafeEntity cafe1 = cafeSaveHelper.saveCafeWith24For7();
		CafeEntity cafe2 = cafeSaveHelper.saveCafeWith24For7();

		JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();
		MemberEntity member = memberReader.read("test@gmail.com");

		CafeTagEntity cafeTag1 = cafeTagSaveHelper.saveCafeTag(CafeTagType.WIFI);
		CafeTagEntity cafeTag2 = cafeTagSaveHelper.saveCafeTag(CafeTagType.OUTLET);

		ReviewEntity review1 = reviewSaveHelper.saveReview(cafe1, member);
		reviewCafeTagSaveHelper.saveReview(review1, cafeTag1);
		ReviewEntity review2 = reviewSaveHelper.saveReview(cafe2, member);
		reviewCafeTagSaveHelper.saveReview(review2, cafeTag1);
		reviewCafeTagSaveHelper.saveReview(review2, cafeTag2);

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
