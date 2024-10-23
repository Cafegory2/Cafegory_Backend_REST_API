package com.example.demo.mapper;

// import com.example.demo.dto.study.StudyOnceCreateResponse;
// import com.example.demo.dto.study.StudyOnceResponse;
// import com.example.demo.dto.study.StudyOnceSearchListResponse;
// import com.example.demo.dto.study.StudyOnceSearchResponse;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.dto.study.CafeStudyCreateResponse;
import com.example.demo.dto.study.CafeStudyDeleteResponse;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.CafeStudy;
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

	public CafeStudy toNewEntity(String studyName, CafeEntity cafeEntity, MemberEntity coordinator, LocalDateTime startDateTime,
								 LocalDateTime endDateTime,
								 MemberComms memberComms, int maxParticipants) {
		StudyPeriod studyPeriod = toStudyPeriod(startDateTime, endDateTime);

		return CafeStudy.builder()
			.name(studyName)
			.cafeEntity(cafeEntity)
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

	public CafeStudyCreateResponse toStudyOnceCreateResponse(CafeStudy createdCafeStudy) {
		return CafeStudyCreateResponse.builder()
			.name(createdCafeStudy.getName())
			.cafeId(createdCafeStudy.getCafeEntity().getId())
			.coordinatorId(createdCafeStudy.getCoordinator().getId())
			.startDateTime(createdCafeStudy.getStudyPeriod().getStartDateTime())
			.endDateTime(createdCafeStudy.getStudyPeriod().getEndDateTime())
			.memberComms(createdCafeStudy.getMemberComms())
			.maxParticipants(createdCafeStudy.getMaxParticipants())
			.nowParticipants(createdCafeStudy.getCafeStudyMembers().size())
			.introduction(createdCafeStudy.getIntroduction())
			.views(createdCafeStudy.getViews())
			.recruitmentStatus(createdCafeStudy.getRecruitmentStatus())
			.build();
	}

	public CafeStudyDeleteResponse toCafeStudyDeleteResponse(CafeStudy cafeStudy) {
		return CafeStudyDeleteResponse.builder()
			.cafeStudyId(cafeStudy.getId())
			.deletedAt(cafeStudy.getDeletedDate())
			.build();
	}
}
