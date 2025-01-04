package com.example.demo.study.infrastructure.repository2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.infrastructure.CafeStudyMemberEntity;
import com.example.demo.study.infrastructure.StudyMemberJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyMemberQueryRepositoryImpl implements StudyMemberQueryRepository {

	private final StudyMemberJpaRepository studyMemberJpaRepository;

	@Override
	public List<Participant> findByMember_Id(MemberId memberId) {
		return studyMemberJpaRepository.findByMember_Id(memberId.getId())
			.stream().map(CafeStudyMemberEntity::toParticipant)
			.collect(Collectors.toList());
	}

	@Override
	public List<Participant> findByStudy_Id(StudyId studyId) {
		return studyMemberJpaRepository.findByCafeStudy_Id(studyId.getId())
			.stream().map(CafeStudyMemberEntity::toParticipant)
			.collect(Collectors.toList());
	}

	@Override
	public int countByCafeStudy_Id(StudyId studyId) {
		return studyMemberJpaRepository.countByCafeStudy_Id(studyId.getId());
	}
}
