package com.example.demo.apidocs;

import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CafeTagPersister.*;
import static com.example.demo.persister.ReviewContextPersister.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.example.demo.auth.implement.token.JwtToken;
import com.example.demo.config.ApiDocsTest;
import com.example.demo.db.cafe.CafeEntity;
import com.example.demo.db.cafe.CafeTagEntity;
import com.example.demo.db.member.MemberEntity;
import com.example.demo.db.member.MemberJpaRepository;
import com.example.demo.domain.cafe.domain.CafeTagType;
import com.example.demo.domain.member.implement.MemberReader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ProfileControllerApiTest extends ApiDocsTest {

	@Autowired
	private MemberReader memberReader;
	@Autowired
	private MemberJpaRepository memberJpaRepository;

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
		CafeEntity cafe1 = aCafe().persistWith24For7();
		CafeEntity cafe2 = aCafe().persistWith24For7();

		JwtToken jwtToken = memberSignupHelper.로그인_되어_있음();
		MemberEntity member = memberJpaRepository.findByEmail("test@gmail.com").get();

		CafeTagEntity wifi = aCafeTag().withType(CafeTagType.WIFI).persist();
		CafeTagEntity outlet = aCafeTag().withType(CafeTagType.OUTLET).persist();

		aReview().withCafe(cafe1).withMember(member).includeTags(wifi).save();
		aReview().withCafe(cafe2).withMember(member).includeTags(wifi, outlet).save();

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
