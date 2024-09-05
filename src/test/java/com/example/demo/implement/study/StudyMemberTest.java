//package com.example.demo.implement.study;
//
//import static com.example.demo.factory.TestMemberFactory.*;
//import static com.example.demo.factory.TestStudyMemberFactory.*;
//import static com.example.demo.factory.TestStudyOnceFactory.*;
//import static org.assertj.core.api.Assertions.*;
//
//import java.time.LocalDateTime;
//import java.util.stream.Stream;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import com.example.demo.implement.member.Member;
//
//class StudyMemberTest {
//
//	private static final LocalDateTime BASE_TIME = LocalDateTime.now();
//
//	@ParameterizedTest
//	@MethodSource("isConflictParameters")
//	@DisplayName("주어진 시간과 겹치는지 확인한다.")
//	void check_if_times_overlap(LocalDateTime givenStartTime, LocalDateTime givenEndTime, boolean expected) {
//		//given
//		LocalDateTime start = BASE_TIME.plusHours(4);
//		LocalDateTime end = BASE_TIME.plusHours(7);
//		Member leader = createMember();
//		StudyOnce studyOnce = createStudyOnceWithTimeAndLeader(start, end, leader);
//		Member member = createMember();
//		StudyMember sut = createStudyMember(member, studyOnce);
//		//when
//		boolean isConflict = sut.isConflictWith(givenStartTime, givenEndTime);
//		//then
//		assertThat(isConflict).isEqualTo(expected);
//	}
//
//	static Stream<Arguments> isConflictParameters() {
//		return Stream.of(
//			Arguments.of(BASE_TIME, BASE_TIME.plusHours(4).minusSeconds(1), false),
//			Arguments.of(BASE_TIME, BASE_TIME.plusHours(4), true),
//			Arguments.of(BASE_TIME.plusHours(5), BASE_TIME.plusHours(6), true),
//			Arguments.of(BASE_TIME.plusHours(5), BASE_TIME.plusHours(7), true),
//			Arguments.of(BASE_TIME.plusHours(5), BASE_TIME.plusHours(8), true),
//			Arguments.of(BASE_TIME.plusHours(7), BASE_TIME.plusHours(9), true),
//			Arguments.of(BASE_TIME.plusHours(7).plusSeconds(1), BASE_TIME.plusHours(9), false)
//		);
//	}
//}
