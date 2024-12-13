package com.example.demo.study.infrastructure.repository2;

import com.example.demo.study.domain.Study;

import java.time.LocalDateTime;

public interface StudyRepository2 {

	//TODO 이름 cascade 변경
	public Study save(Study study, Long memberId);

	void deleteWithCascade(Long studyId, Long memberId, LocalDateTime now);
}
