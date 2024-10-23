package com.example.demo.factory;

import static com.example.demo.implement.study.MemberComms.*;

import java.time.LocalDateTime;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.StudyPeriod;

public class TestCafeStudyFactory {

	public static CafeStudy createCafeStudy(CafeEntity cafeEntity, MemberEntity leader,
                                            LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return CafeStudy.builder()
			.name("카페고리 스터디")
			.cafeEntity(cafeEntity)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(WELCOME)
			.maxParticipants(5)
			.introduction("카페고리 스터디입니다.")
			.build();
	}

	public static CafeStudy createCafeStudyWithName(CafeEntity cafeEntity, MemberEntity leader,
                                                    LocalDateTime startDateTime, LocalDateTime endDateTime, String cafeStudyName) {
		return CafeStudy.builder()
			.name(cafeStudyName)
			.cafeEntity(cafeEntity)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(WELCOME)
			.maxParticipants(5)
			.introduction("카페고리 스터디입니다.")
			.build();
	}

	public static CafeStudy createCafeStudyWithCreatedDate(CafeEntity cafeEntity, MemberEntity leader,
                                                           LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createdDate) {
		return CafeStudy.builder()
			.name("카페고리 스터디")
			.cafeEntity(cafeEntity)
			.coordinator(leader)
			.studyPeriod(createStudyPeriod(startDateTime, endDateTime))
			.memberComms(WELCOME)
			.maxParticipants(5)
			.introduction("카페고리 스터디입니다.")
			.build();
	}

	public static CafeStudy createCafeStudyWithMemberComms(CafeEntity cafeEntity, MemberEntity leader,
                                                           LocalDateTime startDateTime, LocalDateTime endDateTime, MemberComms memberComms) {
		return CafeStudy.builder()
			.name("카페고리 스터디")
			.cafeEntity(cafeEntity)
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
