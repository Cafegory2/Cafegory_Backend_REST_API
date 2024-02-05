package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

	@Test
	@DisplayName("정상 동작 테스트")
	void tryJoin() {
		MemberImpl leader = MemberImpl.builder().id(1L).build();
		MemberImpl member = makeMember(2, LocalDateTime.now().plusHours(9), LocalDateTime.now().plusHours(13));
		StudyOnceImpl studyOnce = makeStudy(leader, LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(8));
		studyOnce.tryJoin(member);
		List<MemberImpl> collect = studyOnce.getStudyMembers()
			.stream()
			.map(StudyMember::getMember)
			.collect(Collectors.toList());
		org.assertj.core.api.Assertions.assertThat(collect).contains(member);
	}

	private static StudyOnceImpl makeStudy(MemberImpl leader, LocalDateTime start, LocalDateTime end) {
		return StudyOnceImpl.builder()
			.startDateTime(start)
			.endDateTime(end)
			.leader(leader)
			.build();
	}

	private static MemberImpl makeMember(long memberId, LocalDateTime start, LocalDateTime end) {
		MemberImpl member = MemberImpl.builder().id(memberId).build();
		StudyMember studyMember = new StudyMember(member, makeStudy(member, start, end));
		member.setStudyMembers(new ArrayList<>(List.of(studyMember)));
		return member;
	}

	@Test
	@DisplayName("이미 참여중인 카공이 있어서 실패하는 테스트")
	void tryJoinFailByConflict() {
		MemberImpl leader = MemberImpl.builder().id(1L).build();
		MemberImpl member = makeMember(2, LocalDateTime.now().plusHours(9), LocalDateTime.now().plusHours(13));
		StudyOnceImpl studyOnce = makeStudy(leader, LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(9));
		org.assertj.core.api.Assertions.assertThatThrownBy(() -> studyOnce.tryJoin(member))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("해당 시간에 참여중인 카공이 이미 있습니다.");
	}

	@Test
	@DisplayName("이미 참여중인 카공이라 실패하는 테스트")
	void tryJoinFailByDuplicate() {
		MemberImpl leader = MemberImpl.builder().id(1L).build();
		MemberImpl member = makeMember(2, LocalDateTime.now().plusHours(9), LocalDateTime.now().plusHours(13));
		StudyOnceImpl studyOnce = makeStudy(leader, LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(8));
		studyOnce.tryJoin(member);
		org.assertj.core.api.Assertions.assertThatThrownBy(() -> studyOnce.tryJoin(member))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("이미 참여중인 카공입니다.");
	}

	@Test
	@DisplayName("카공 참여 취소 테스트")
	void tryQuit() {
		MemberImpl leader = MemberImpl.builder().id(1L).build();
		MemberImpl member = MemberImpl.builder().id(2L).build();
		StudyOnceImpl studyOnce = makeStudy(leader, LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(8));
		studyOnce.tryJoin(member);
		studyOnce.tryQuit(member, LocalDateTime.now().plusHours(3).minusSeconds(1));
	}

	@Test
	@DisplayName("이미 카공 참여 인원이 확정되어 카공 참여 취소가 실패하는 테스트")
	void tryQuitFailByTimeOver() {
		MemberImpl leader = MemberImpl.builder().id(1L).build();
		MemberImpl member = MemberImpl.builder().id(2L).build();
		StudyOnceImpl studyOnce = makeStudy(leader, LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(8));
		studyOnce.tryJoin(member);
		org.assertj.core.api.Assertions.assertThatThrownBy(
				() -> studyOnce.tryQuit(member, LocalDateTime.now().plusHours(3)))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("카공 인원 모집이 확정된 이후 참여 취소를 할 수 없습니다.");
	}

	@Test
	@DisplayName("참여중인 카공이 아니라서 카공 참여 취소가 실패하는 테스트")
	void tryQuitFailByNotJoin() {
		MemberImpl leader = MemberImpl.builder().id(1L).build();
		MemberImpl member = MemberImpl.builder().id(2L).build();
		StudyOnceImpl studyOnce = makeStudy(leader, LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(8));
		org.assertj.core.api.Assertions.assertThatThrownBy(
				() -> studyOnce.tryQuit(member, LocalDateTime.now()))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("참여중인 카공이 아닙니다.");
	}
}
