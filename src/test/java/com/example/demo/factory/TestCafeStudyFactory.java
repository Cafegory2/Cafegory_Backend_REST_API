package com.example.demo.factory;

import java.time.LocalDateTime;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.StudyPeriod;

import static com.example.demo.implement.study.MemberComms.POSSIBLE;

public class TestCafeStudyFactory {

	public static CafeStudy createCafeStudyWithName(Cafe cafe, Member leader,
													LocalDateTime startDateTime, LocalDateTime endDateTime, String cafeStudyName) {
		return CafeStudy.builder()
			.name(cafeStudyName)
			.cafe(cafe)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(POSSIBLE)
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
}
