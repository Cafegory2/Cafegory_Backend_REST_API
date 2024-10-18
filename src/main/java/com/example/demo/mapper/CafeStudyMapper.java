package com.example.demo.mapper;

// import com.example.demo.dto.study.StudyOnceCreateResponse;
// import com.example.demo.dto.study.StudyOnceResponse;
// import com.example.demo.dto.study.StudyOnceSearchListResponse;
// import com.example.demo.dto.study.StudyOnceSearchResponse;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.dto.study.CafeStudyCreateResponse;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.StudyPeriod;

@Component
public class CafeStudyMapper {
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

	public CafeStudyEntity toNewEntity(String studyName, Cafe cafe, Member coordinator, LocalDateTime startDateTime,
									   LocalDateTime endDateTime,
									   MemberComms memberComms, int maxParticipants) {
		StudyPeriod studyPeriod = toStudyPeriod(startDateTime, endDateTime);

		return CafeStudyEntity.builder()
			.name(studyName)
			.cafe(cafe)
			.coordinator(coordinator)
			.studyPeriod(studyPeriod)
			.memberComms(memberComms)
			.maxParticipants(maxParticipants)
			.build();
	}

	private StudyPeriod toStudyPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return StudyPeriod.builder().startDateTime(startDateTime).endDateTime(endDateTime).build();
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

	public CafeStudyCreateResponse toStudyOnceCreateResponse(CafeStudyEntity createdCafeStudyEntity) {
		return CafeStudyCreateResponse.builder()
			.name(createdCafeStudyEntity.getName())
			.cafeId(createdCafeStudyEntity.getCafe().getId())
			.coordinatorId(createdCafeStudyEntity.getCoordinator().getId())
			.startDateTime(createdCafeStudyEntity.getStudyPeriod().getStartDateTime())
			.endDateTime(createdCafeStudyEntity.getStudyPeriod().getEndDateTime())
			.memberComms(createdCafeStudyEntity.getMemberComms())
			.maxParticipants(createdCafeStudyEntity.getMaxParticipants())
			.nowParticipants(createdCafeStudyEntity.getCafeStudyMembers().size())
			.introduction(createdCafeStudyEntity.getIntroduction())
			.views(createdCafeStudyEntity.getViews())
			.recruitmentStatus(createdCafeStudyEntity.getRecruitmentStatus())
			.build();
	}

}
