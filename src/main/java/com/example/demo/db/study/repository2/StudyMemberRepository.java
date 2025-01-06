package com.example.demo.db.study.repository2;

import java.time.LocalDateTime;

import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.StudyId;
import com.example.demo.domain.study.domain.StudyMemberId;
import com.example.demo.domain.study.domain.StudyRole;

public interface StudyMemberRepository {

	StudyMemberId save(MemberId memberId, StudyId studyId, StudyRole studyRole);

	void remove(StudyId studyId, MemberId memberId, LocalDateTime now);
}
