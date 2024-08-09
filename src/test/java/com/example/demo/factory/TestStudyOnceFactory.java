//package com.example.demo.factory;
//
//import java.time.LocalDateTime;
//
//import com.example.demo.domain.cafe.Cafe;
//import com.example.demo.domain.member.Member;
//import com.example.demo.domain.study.StudyOnce;
//
//public class TestStudyOnceFactory {
//
//	public static StudyOnce createStudyOnce(Cafe cafe, Member leader) {
//		return StudyOnce.builder()
//			.name("카페 스터디")
//			.startDateTime(LocalDateTime.of(2999, 2, 16, 12, 0))
//			.endDateTime(LocalDateTime.of(2999, 2, 16, 15, 0))
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
//}
