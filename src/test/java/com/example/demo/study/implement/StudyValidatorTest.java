package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.demo.config.FakeTimeUtil;
import com.example.demo.exception.CafegoryException;

class StudyValidatorTest {

	private StudyValidator sut = new StudyValidator(new FakeTimeUtil());

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	@DisplayName("공백, 빈값 검증")
	void validate_empty_or_whiteSpace(String value) {
		assertThatThrownBy(
			() -> sut.validateEmptyOrWhiteSpace(value, STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE.getErrorMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "aaaaaaaaaaaaaaaaaaaaa"})
	@DisplayName("이름의 길이는 빈 문자열이나 20자 초과일 수 없다")
	void fail_validate_name_length(String value) {
		assertThatThrownBy(() -> sut.validateNameLength(value))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(CAFE_STUDY_INVALID_NAME.getErrorMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"a", "aaaaaaaaaaaaaaaaaaaa"})
	@DisplayName("이름의 길이는 1자 이상 20자 이하이다.")
	void success_validate_name_length(String value) {
		assertDoesNotThrow(() -> sut.validateNameLength(value));
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 7})
	@DisplayName("최대 참여 인원수는 2명 미만 6초과일 수 없다.")
	void fail_validate_max_participants(int value) {
		assertThatThrownBy(() -> sut.validateMaxParticipants(value))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_LIMIT_MEMBER_CAPACITY.getErrorMessage());
	}

	@ParameterizedTest
	@ValueSource(ints = {2, 6})
	@DisplayName("최대 참여 인원수는 2명 이상 6명 이하이다.")
	void validate_max_participants(int value) {
		assertDoesNotThrow(() -> sut.validateMaxParticipants(value));
	}

	@Test
	@DisplayName("멤버가 작성자인지 검증한다.")
	void isAuthor() {
		assertDoesNotThrow(
			() -> sut.validateMemberIsCafeStudyCoordinator(1L, 1L)
		);
	}

	@Test
	@DisplayName("스터디에 카공장만 존재한다")
	void validate_study_member_is_coordinator_only() {
		Long coordinatorId = 1L;
		List<Long> participantsIds = List.of(coordinatorId);

		assertDoesNotThrow(
			() -> sut.validateCafeStudyMembersPresent(coordinatorId, participantsIds)
		);
	}

	@Test
	@DisplayName("스터디에 카공장외에 다른 참가자도 존재한다")
	void validate_study_member_is_not_coordinator_only() {
		Long coordinatorId = 1L;
		List<Long> participantsIds = List.of(coordinatorId, 2L);

		assertThatThrownBy(
			() -> sut.validateCafeStudyMembersPresent(coordinatorId, participantsIds)
		).isInstanceOf(CafegoryException.class)
			.hasMessage(CAFE_STUDY_DELETE_FAIL_MEMBERS_PRESENT.getErrorMessage());
	}

}
