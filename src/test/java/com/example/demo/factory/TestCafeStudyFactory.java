package com.example.demo.factory;

import static com.example.demo.implement.study.MemberComms.*;

import java.time.LocalDateTime;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.StudyPeriod;

public class TestCafeStudyFactory {

	public static CafeStudyEntity createCafeStudy(CafeEntity cafe, MemberEntity leader,
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

	public static CafeStudyEntity createCafeStudyWithName(CafeEntity cafe, MemberEntity leader,
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

	public static CafeStudyEntity createCafeStudyWithCreatedDate(CafeEntity cafe, MemberEntity leader,
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

	public static CafeStudyEntity createCafeStudyWithMemberComms(CafeEntity cafe, MemberEntity leader,
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
