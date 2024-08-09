//package com.example.demo.domain.member;
//
//import static com.example.demo.exception.ExceptionType.*;
//import static com.example.demo.factory.TestMemberFactory.*;
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import com.example.demo.exception.CafegoryException;
//
//class MemberTest {
//
//	@Test
//	@DisplayName("자기소개 글자수가 300자를 넘으면 업데이트 실패한다.")
//	void introduction_length_is_too_long() {
//		//given
//		String introduction = "a".repeat(301);
//		Member sut = createMember();
//		//then
//		assertThatThrownBy(() -> sut.updateProfile("테스트", introduction))
//			.isInstanceOf(CafegoryException.class)
//			.hasMessage(PROFILE_UPDATE_INVALID_INTRODUCTION.getErrorMessage());
//	}
//
//	@ParameterizedTest
//	@ValueSource(ints = {0, 300})
//	@DisplayName("자기소개 글자수는 최소 0자, 최대 300자까지 가능하다.")
//	void introduction_length_is_valid(int introductionLength) {
//		//given
//		String introduction = "a".repeat(introductionLength);
//		Member sut = createMember();
//		//then
//		assertDoesNotThrow(() -> sut.updateProfile("테스트", introduction));
//	}
//}
