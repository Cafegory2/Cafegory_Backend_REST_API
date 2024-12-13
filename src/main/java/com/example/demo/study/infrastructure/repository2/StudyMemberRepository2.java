package com.example.demo.study.infrastructure.repository2;

import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.StudyRole;

public interface StudyMemberRepository2 {

	Participant save(Long memberId, Long studyId, StudyRole studyRole);
}
