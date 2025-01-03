package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyId;

public interface StudyRepository2 {

	Long save(Study study, Long memberId);

	StudyId save(Study study, MemberId memberId);

	void deleteWithCascade(StudyId studyId, MemberId memberId, LocalDateTime now);
}
