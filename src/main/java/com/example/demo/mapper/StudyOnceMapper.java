package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.domain.cafe.Cafe;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.study.StudyOnce;
import com.example.demo.dto.cafe.CafeSearchStudyOnceResponse;
import com.example.demo.dto.study.StudyOnceCreateRequest;
import com.example.demo.dto.study.StudyOnceCreateResponse;
import com.example.demo.dto.study.StudyOnceResponse;
import com.example.demo.dto.study.StudyOnceSearchListResponse;
import com.example.demo.dto.study.StudyOnceSearchResponse;

public class StudyOnceMapper {

	public List<CafeSearchStudyOnceResponse> toCafeSearchStudyOnceResponse(Cafe findCafe) {
		return findCafe.getStudyOnceGroup().stream()
			.map(studyOnce ->
				CafeSearchStudyOnceResponse.builder()
					.cafeId(findCafe.getId())
					.studyOnceId(studyOnce.getId())
					.name(studyOnce.getName())
					.startDateTime(studyOnce.getStartDateTime())
					.endDateTime(studyOnce.getEndDateTime())
					.maxMemberCount(studyOnce.getMaxMemberCount())
					.nowMemberCount(studyOnce.getNowMemberCount())
					.isEnd(studyOnce.isAbleToTalk())
					.build()
			)
			.collect(Collectors.toList());
	}

	public StudyOnce toNewEntity(StudyOnceCreateRequest studyOnceCreateRequest, Cafe cafe,
		Member leader) {
		return StudyOnce.builder()
			.name(studyOnceCreateRequest.getName())
			.startDateTime(studyOnceCreateRequest.getStartDateTime())
			.endDateTime(studyOnceCreateRequest.getEndDateTime())
			.maxMemberCount(studyOnceCreateRequest.getMaxMemberCount())
			.nowMemberCount(0)
			.isEnd(false)
			.ableToTalk(studyOnceCreateRequest.isCanTalk())
			.openChatUrl(studyOnceCreateRequest.getOpenChatUrl())
			.cafe(cafe)
			.leader(leader)
			.build();
	}

	public StudyOnceResponse toStudyOnceResponse(StudyOnce saved, boolean canJoin) {
		return StudyOnceResponse.builder()
			.cafeId(saved.getCafe().getId())
			.area(saved.getCafe().getRegion())
			.studyOnceId(saved.getId())
			.name(saved.getName())
			.startDateTime(saved.getStartDateTime())
			.endDateTime(saved.getEndDateTime())
			.maxMemberCount(saved.getMaxMemberCount())
			.nowMemberCount(saved.getNowMemberCount())
			.canTalk(saved.isAbleToTalk())
			.canJoin(canJoin)
			.isEnd(saved.isEnd())
			.openChatUrl(saved.getOpenChatUrl())
			.build();
	}

	public StudyOnceSearchResponse toStudyOnceSearchResponse(StudyOnce saved, boolean canJoin, boolean isAttendance) {
		return StudyOnceSearchResponse.builder()
			.cafeId(saved.getCafe().getId())
			.creatorId(saved.getLeader().getId())
			.area(saved.getCafe().getRegion())
			.studyOnceId(saved.getId())
			.name(saved.getName())
			.startDateTime(saved.getStartDateTime())
			.endDateTime(saved.getEndDateTime())
			.maxMemberCount(saved.getMaxMemberCount())
			.nowMemberCount(saved.getStudyMembers().size())
			.canTalk(saved.isAbleToTalk())
			.canJoin(canJoin)
			.isEnd(saved.isEnd())
			.openChatUrl(saved.getOpenChatUrl())
			.isAttendance(isAttendance)
			.build();
	}

	public StudyOnceSearchListResponse toStudyOnceSearchListResponse(StudyOnce saved, boolean canJoin) {
		return StudyOnceSearchListResponse.builder()
			.cafeId(saved.getCafe().getId())
			.cafeName(saved.getCafe().getName())
			.area(saved.getCafe().getRegion())
			.studyOnceId(saved.getId())
			.name(saved.getName())
			.startDateTime(saved.getStartDateTime())
			.endDateTime(saved.getEndDateTime())
			.maxMemberCount(saved.getMaxMemberCount())
			.nowMemberCount(saved.getNowMemberCount())
			.canTalk(saved.isAbleToTalk())
			.canJoin(canJoin)
			.isEnd(saved.isEnd())
			.build();
	}

	public StudyOnceCreateResponse toStudyOnceCreateResponse(StudyOnce saved, boolean canJoin) {
		return StudyOnceCreateResponse.builder()
			.cafeId(saved.getCafe().getId())
			.area(saved.getCafe().getRegion())
			.studyOnceId(saved.getId())
			.name(saved.getName())
			.startDateTime(saved.getStartDateTime())
			.endDateTime(saved.getEndDateTime())
			.maxMemberCount(saved.getMaxMemberCount())
			.nowMemberCount(saved.getNowMemberCount())
			.canTalk(saved.isAbleToTalk())
			.canJoin(canJoin)
			.isEnd(saved.isEnd())
			.openChatUrl(saved.getOpenChatUrl())
			.build();
	}

}
