package com.example.demo.domain.member;

import static com.example.demo.exception.ExceptionType.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.exception.CafegoryException;
import com.example.demo.factory.TestMemberFactory;

class MemberTest {

	@Test
	@DisplayName("자기소개가 너무 긴 경우 수정이 실패하는지 확인")
	void updateProfileFailWhenTooShortIntroduction() {
		Member sut = TestMemberFactory.createMember();
		final int tooLongIntroductionLength = 301;
		String introduction = "a".repeat(tooLongIntroductionLength);

		Assertions.assertThatThrownBy(() -> sut.updateProfile("테스트", introduction))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(PROFILE_UPDATE_INVALID_INTRODUCTION.getErrorMessage());
	}
}
