package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyId;

public interface StudyRepository2 {

	Long save(Study study, Long memberId);

	StudyId save(Study study, MemberId memberId);

	//TODO 이름 cascade 변경
	//	public Study saveWithCascade(Study study, Long memberId);

	void deleteWithCascade(Long studyId, Long memberId, LocalDateTime now);
}
