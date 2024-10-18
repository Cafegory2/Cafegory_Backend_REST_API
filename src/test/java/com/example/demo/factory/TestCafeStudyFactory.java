package com.example.demo.factory;

import java.time.LocalDateTime;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.StudyPeriod;

import static com.example.demo.implement.study.MemberComms.*;

public class TestCafeStudyFactory {

	public static CafeStudyEntity createCafeStudy(Cafe cafe, Member leader,
												  LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return CafeStudyEntity.builder()
			.name("카페고리 스터디")
			.cafe(cafe)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(WELCOME)
			.maxParticipants(5)
			.introduction("카페고리 스터디입니다.")
			.build();
	}

	public static CafeStudyEntity createCafeStudyWithName(Cafe cafe, Member leader,
														  LocalDateTime startDateTime, LocalDateTime endDateTime, String cafeStudyName) {
		return CafeStudyEntity.builder()
			.name(cafeStudyName)
			.cafe(cafe)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(WELCOME)
			.maxParticipants(5)
			.introduction("카페고리 스터디입니다.")
			.build();
	}

	public static CafeStudyEntity createCafeStudyWithCreatedDate(Cafe cafe, Member leader,
																 LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createdDate) {
		return CafeStudyEntity.builder()
			.name("카페고리 스터디")
			.cafe(cafe)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(WELCOME)
			.maxParticipants(5)
			.introduction("카페고리 스터디입니다.")
			.build();
	}

	public static CafeStudyEntity createCafeStudyWithMemberComms(Cafe cafe, Member leader,
																 LocalDateTime startDateTime, LocalDateTime endDateTime, MemberComms memberComms) {
		return CafeStudyEntity.builder()
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
