package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyRole;

public interface StudyMemberRepository2 {

	Participant save(Long memberId, Long studyId, StudyRole studyRole);

	Participant save(MemberId memberId, StudyId studyId, StudyRole studyRole);

	void remove(Long studyId, Long memberId, LocalDateTime now);
}
