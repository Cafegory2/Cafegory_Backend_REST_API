package com.example.demo.study.infrastructure.repository2;

import java.util.List;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.StudyId;

public interface StudyMemberQueryRepository2 {

	List<Participant> findByMember_Id(MemberId memberId);

	List<Participant> findByStudy_Id(StudyId studyId);

	int countByCafeStudy_Id(StudyId studyId);
}
