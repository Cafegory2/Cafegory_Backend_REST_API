package com.example.demo.study.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.ParticipantCount;
import com.example.demo.study.infrastructure.CafeStudyMemberEntity;
import com.example.demo.study.infrastructure.StudyMemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyMemberReader {

	private final StudyMemberRepository studyMemberRepository;

	public int loadParticipantCount(Long cafeStudyId) {
		return studyMemberRepository.countByCafeStudy_Id(cafeStudyId);
	}

	public List<Participant> readMyUpcomingsBy(Long memberId) {
		return studyMemberRepository.findByMember_Id(memberId)
			.stream().map(CafeStudyMemberEntity::toParticipant)
			.collect(Collectors.toList());
	}

	// study에 참여한 참여자를 가져온다.
	public List<Long> readParticipantIdsBy(Long studyId) {
		return studyMemberRepository.findByCafeStudy_Id(studyId).stream()
			.map(CafeStudyMemberEntity::getId)
			.collect(Collectors.toList());
	}

	public ParticipantCount readParticipantCountBy(Long studyId) {
		return ParticipantCount.builder()
			.currentCount(readParticipantIdsBy(studyId).size())
			.build();
	}
}
