package com.example.demo.domain.study.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.db.study.repository2.StudyMemberQueryRepository;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.Participant;
import com.example.demo.domain.study.domain.StudyId;
import com.example.demo.domain.study.domain.StudyMemberId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyMemberReader {

	private final StudyMemberQueryRepository studyMemberQueryRepository;

	public int loadParticipantCount(StudyId studyId) {
		return studyMemberQueryRepository.countByCafeStudy_Id(studyId);
	}

	public List<Participant> readMyUpcomingsBy(MemberId memberId) {
		return studyMemberQueryRepository.findByMember_Id(memberId);
	}

	// study에 참여한 참여자를 가져온다.
	public List<StudyMemberId> readParticipantIdsBy(StudyId studyId) {
		return studyMemberQueryRepository.findByStudy_Id(studyId).stream()
			.map(participant -> new StudyMemberId(participant.getId().getId()))
			.collect(Collectors.toList());
	}

	public int readParticipantCountBy(StudyId studyId) {
		return readParticipantIdsBy(studyId).size();
	}
}
