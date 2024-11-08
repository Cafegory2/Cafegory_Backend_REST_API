package com.example.demo.study.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.study.domain.Participant;
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

	public List<Participant> read(Long memberId) {
		return studyMemberRepository.findByMember_Id(memberId)
			.stream().map(CafeStudyMemberEntity::toParticipant)
			.collect(Collectors.toList());
	}

}
