package com.example.demo.factory;

<<<<<<< HEAD
import static com.example.demo.implement.study.MemberComms.*;

=======
<<<<<<< HEAD
>>>>>>> d0e38a8 (feat: 스터디 삭제하는 기능 구현)
import java.time.LocalDateTime;
=======
import static com.example.demo.config.FakeTimeUtil.*;
>>>>>>> ba34f9b (test: 스터디 삭제 테스트 작성)

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.CafeStudy;
<<<<<<< HEAD
import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.StudyPeriod;

public class TestCafeStudyFactory {

	public static CafeStudy createCafeStudy(Cafe cafe, Member leader,
		LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return CafeStudy.builder()
			.name("카페고리 스터디")
			.cafe(cafe)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(WELCOME)
			.maxParticipants(5)
			.introduction("카페고리 스터디입니다.")
			.build();
	}

	public static CafeStudy createCafeStudyWithName(Cafe cafe, Member leader,
		LocalDateTime startDateTime, LocalDateTime endDateTime, String cafeStudyName) {
		return CafeStudy.builder()
			.name(cafeStudyName)
			.cafe(cafe)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(WELCOME)
			.maxParticipants(5)
			.introduction("카페고리 스터디입니다.")
			.build();
	}

	public static CafeStudy createCafeStudyWithCreatedDate(Cafe cafe, Member leader,
		LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createdDate) {
		return CafeStudy.builder()
			.name("카페고리 스터디")
			.cafe(cafe)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(WELCOME)
			.maxParticipants(5)
			.introduction("카페고리 스터디입니다.")
			.build();
	}

	public static CafeStudy createCafeStudyWithMemberComms(Cafe cafe, Member leader,
		LocalDateTime startDateTime, LocalDateTime endDateTime, MemberComms memberComms) {
		return CafeStudy.builder()
			.name("카페고리 스터디")
			.cafe(cafe)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(memberComms)
			.maxParticipants(5)
			.introduction("카페고리 스터디입니다.")
			.build();
	}

	private static StudyPeriod createStudyPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return StudyPeriod.builder()
			.startDateTime(startDateTime)
			.endDateTime(endDateTime)
			.build();
	}
=======
import com.example.demo.implement.study.StudyPeriod;

import lombok.RequiredArgsConstructor;

//package com.example.demo.factory;
//
//import java.time.LocalDateTime;
//
//import com.example.demo.implement.cafe.Cafe;
//import com.example.demo.implement.member.Member;
//import com.example.demo.implement.study.StudyOnce;
//

@RequiredArgsConstructor
public class TestCafeStudyFactory {

	public static CafeStudy createCafeStudy(Cafe cafe, Member coordinator) {
		return CafeStudy.builder()
			.name("카페 스터디")
			.cafe(cafe)
			.coordinator(coordinator)
			.studyPeriod(StudyPeriod.builder()
				.startDateTime(DEFAULT_FIXED_DATE_TIME)
				.endDateTime(DEFAULT_FIXED_DATE_TIME.plusHours(1))
				.build())
			.build();
	}

	//	public static StudyOnce createStudyOnceWithTime(Cafe cafe, Member leader, LocalDateTime startDateTime,
	//		LocalDateTime endDateTime) {
	//		return StudyOnce.builder()
	//			.name("카페 스터디")
	//			.startDateTime(startDateTime)
	//			.endDateTime(endDateTime)
	//			.maxMemberCount(5)
	//			.nowMemberCount(0)
	//			.isEnd(false)
	//			.ableToTalk(true)
	//			.openChatUrl("오픈채팅방 링크")
	//			.cafe(cafe)
	//			.leader(leader)
	//			.build();
	//	}
	//
	//	public static StudyOnce createStudyOnceWithTimeAndLeader(LocalDateTime startDateTime,
	//		LocalDateTime endDateTime, Member leader) {
	//		return StudyOnce.builder()
	//			.name("카페 스터디")
	//			.startDateTime(startDateTime)
	//			.endDateTime(endDateTime)
	//			.maxMemberCount(5)
	//			.nowMemberCount(0)
	//			.isEnd(false)
	//			.ableToTalk(true)
	//			.openChatUrl("오픈채팅방 링크")
	//			.leader(leader)
	//			.build();
	//	}
>>>>>>> ba34f9b (test: 스터디 삭제 테스트 작성)
}
