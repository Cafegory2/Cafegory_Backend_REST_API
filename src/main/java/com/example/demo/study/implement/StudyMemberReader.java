package com.example.demo.study.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.ParticipantCount;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyMemberId;
import com.example.demo.study.infrastructure.repository2.StudyMemberQueryRepository2;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyMemberReader {

	private final StudyMemberQueryRepository2 studyMemberQueryRepository2;

	public int loadParticipantCount(StudyId studyId) {
		return studyMemberQueryRepository2.countByCafeStudy_Id(studyId);
	}

	public List<Participant> readMyUpcomingsBy(MemberId memberId) {
		return studyMemberQueryRepository2.findByMember_Id(memberId);
	}

	// study에 참여한 참여자를 가져온다.
	public List<StudyMemberId> readParticipantIdsBy(StudyId studyId) {
		return studyMemberQueryRepository2.findByStudy_Id(studyId).stream()
			.map(participant -> new StudyMemberId(participant.getId().getId()))
			.collect(Collectors.toList());
	}

	public ParticipantCount readParticipantCountBy(StudyId studyId) {
		List<StudyMemberId> studyMemberIds = readParticipantIdsBy(studyId);

		return ParticipantCount.builder()
				.currentCount(studyMemberIds.size())
				.build();
	}
}
