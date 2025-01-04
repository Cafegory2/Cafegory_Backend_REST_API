package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyMemberId;
import com.example.demo.study.domain.StudyRole;

public interface StudyMemberRepository2 {

	StudyMemberId save(MemberId memberId, StudyId studyId, StudyRole studyRole);

	void remove(StudyId studyId, MemberId memberId, LocalDateTime now);
}
