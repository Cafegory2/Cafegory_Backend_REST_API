package com.example.demo.factory;

import java.time.LocalDateTime;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.StudyPeriod;

import static com.example.demo.implement.study.MemberComms.*;

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
}
