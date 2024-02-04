package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StudyOnceImplTest {

	@ParameterizedTest
	@MethodSource("canJoinParameter")
	@DisplayName("참여 가능 여부 결정 테스트")
	void canJoin(LocalDateTime base, LocalDateTime start, boolean expected) {
		StudyOnceImpl studyOnce = StudyOnceImpl.builder()
			.startDateTime(start)
			.endDateTime(start.plusHours(4))
			.leader(MemberImpl.builder().build())
			.build();
		boolean canJoin = studyOnce.canJoin(base);
		Assertions.assertEquals(expected, canJoin);
	}

	@Test
	@DisplayName("정상적으로 생성되었을 때 의존성 제대로 설정되었는지 확인하는 테스트")
	void create() {
		LocalDateTime start = LocalDateTime.now().plusHours(3).plusMinutes(1);
		MemberImpl leader = MemberImpl.builder().id(1L).build();
		StudyOnceImpl studyOnce = StudyOnceImpl.builder()
			.startDateTime(start)
			.endDateTime(start.plusHours(4))
			.leader(leader)
			.build();
		Assertions.assertEquals(studyOnce, studyOnce.getStudyMembers().get(0).getStudy());
		Assertions.assertEquals(studyOnce.getLeader(), studyOnce.getStudyMembers().get(0).getMember());
	}

	static Stream<Arguments> canJoinParameter() {
		LocalDateTime start = LocalDateTime.now().plusHours(4);
		return Stream.of(
			Arguments.of(start.minusHours(1), start, true),
			Arguments.of(start.minusHours(1).plusSeconds(1), start, false)
		);
	}
}
