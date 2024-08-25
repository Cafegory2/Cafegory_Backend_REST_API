package com.example.demo.mapper;

// import com.example.demo.dto.study.StudyOnceCreateResponse;
// import com.example.demo.dto.study.StudyOnceResponse;
// import com.example.demo.dto.study.StudyOnceSearchListResponse;
// import com.example.demo.dto.study.StudyOnceSearchResponse;

import java.time.LocalDateTime;

import com.example.demo.domain.cafe.Cafe;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.study.CafeStudy;
import com.example.demo.domain.study.StudyPeriod;
import com.example.demo.dto.study.CafeStudyCreateRequest;

public class StudyOnceMapper {
	// public List<CafeSearchStudyOnceResponse> toCafeSearchStudyOnceResponse(Cafe findCafe) {
	// 	return findCafe.getStudyOnceGroup().stream()
	// 		.map(studyOnce ->
	// 			CafeSearchStudyOnceResponse.builder()
	// 				.cafeId(findCafe.getId())
	// 				.studyOnceId(studyOnce.getId())
	// 				.name(studyOnce.getName())
	// 				.startDateTime(studyOnce.getStartDateTime())
	// 				.endDateTime(studyOnce.getEndDateTime())
	// 				.maxMemberCount(studyOnce.getMaxMemberCount())
	// 				.nowMemberCount(studyOnce.getStudyMembers().size())
	// 				.isEnd(studyOnce.isAbleToTalk())
	// 				.build()
	// 		)
	// 		.collect(Collectors.toList());
	// }

	public CafeStudy toNewEntity(CafeStudyCreateRequest cafeStudyCreateRequest, Cafe cafe, Member coordinator) {
		StudyPeriod studyPeriod = toStudyPeriod(cafeStudyCreateRequest.getStartDateTime(),
			cafeStudyCreateRequest.getEndDateTime());

		return CafeStudy.builder()
			.name(cafeStudyCreateRequest.getName())
			.cafe(cafe)
			.coordinator(coordinator)
			.studyPeriod(studyPeriod)
			.memberComms(cafeStudyCreateRequest.getMemberComms())
			.maxParticipants(cafeStudyCreateRequest.getMaxParticipants())
			.build();
	}

	private StudyPeriod toStudyPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return StudyPeriod.builder()
			.startDateTime(startDateTime)
			.endDateTime(endDateTime)
			.build();
	}

	// public StudyOnceResponse toStudyOnceResponse(CafeStudy saved, boolean canJoin) {
	// 	return StudyOnceResponse.builder()
	// 		.cafeId(saved.getCafe().getId())
	// 		.area(saved.getCafe().getRegion())
	// 		.studyOnceId(saved.getId())
	// 		.name(saved.getName())
	// 		.startDateTime(saved.getStartDateTime())
	// 		.endDateTime(saved.getEndDateTime())
	// 		.maxMemberCount(saved.getMaxMemberCount())
	// 		.nowMemberCount(saved.getStudyMembers().size())
	// 		.canTalk(saved.isAbleToTalk())
	// 		.canJoin(canJoin)
	// 		.isEnd(saved.isEnd())
	// 		.openChatUrl(saved.getOpenChatUrl())
	// 		.build();
	// }

	// public StudyOnceSearchResponse toStudyOnceSearchResponse(CafeStudy saved, boolean canJoin, boolean isAttendance) {
	// 	return StudyOnceSearchResponse.builder()
	// 		.cafeId(saved.getCafe().getId())
	// 		.creatorId(saved.getLeader().getId())
	// 		.area(saved.getCafe().getRegion())
	// 		.studyOnceId(saved.getId())
	// 		.name(saved.getName())
	// 		.startDateTime(saved.getStartDateTime())
	// 		.endDateTime(saved.getEndDateTime())
	// 		.maxMemberCount(saved.getMaxMemberCount())
	// 		.nowMemberCount(saved.getStudyMembers().size())
	// 		.canTalk(saved.isAbleToTalk())
	// 		.canJoin(canJoin)
	// 		.isEnd(saved.isEnd())
	// 		.openChatUrl(saved.getOpenChatUrl())
	// 		.isAttendance(isAttendance)
	// 		.build();
	// }
	//
	// public StudyOnceSearchListResponse toStudyOnceSearchListResponse(CafeStudy saved, boolean canJoin) {
	// 	return StudyOnceSearchListResponse.builder()
	// 		.cafeId(saved.getCafe().getId())
	// 		.cafeName(saved.getCafe().getName())
	// 		.area(saved.getCafe().getRegion())
	// 		.studyOnceId(saved.getId())
	// 		.name(saved.getName())
	// 		.startDateTime(saved.getStartDateTime())
	// 		.endDateTime(saved.getEndDateTime())
	// 		.maxMemberCount(saved.getMaxMemberCount())
	// 		.nowMemberCount(saved.getStudyMembers().size())
	// 		.canTalk(saved.isAbleToTalk())
	// 		.canJoin(canJoin)
	// 		.isEnd(saved.isEnd())
	// 		.build();
	// }
	//
	// public StudyOnceCreateResponse toStudyOnceCreateResponse(CafeStudy saved, boolean canJoin) {
	// 	return StudyOnceCreateResponse.builder()
	// 		.cafeId(saved.getCafe().getId())
	// 		.area(saved.getCafe().getRegion())
	// 		.studyOnceId(saved.getId())
	// 		.name(saved.getName())
	// 		.startDateTime(saved.getStartDateTime())
	// 		.endDateTime(saved.getEndDateTime())
	// 		.maxMemberCount(saved.getMaxMemberCount())
	// 		.nowMemberCount(saved.getStudyMembers().size())
	// 		.canTalk(saved.isAbleToTalk())
	// 		.canJoin(canJoin)
	// 		.isEnd(saved.isEnd())
	// 		.openChatUrl(saved.getOpenChatUrl())
	// 		.build();
	// }
	//
}
