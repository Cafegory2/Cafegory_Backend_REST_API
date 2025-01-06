package com.example.demo.domain.study.repository;

import java.util.List;

import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.Participant;
import com.example.demo.domain.study.domain.StudyId;

public interface StudyMemberQueryRepository {

	List<Participant> findByMember_Id(MemberId memberId);

	List<Participant> findByStudy_Id(StudyId studyId);

	int countByCafeStudy_Id(StudyId studyId);
}
