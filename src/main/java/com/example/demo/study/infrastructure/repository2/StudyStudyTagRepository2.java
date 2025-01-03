package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.study.domain.StudyId;

public interface StudyStudyTagRepository2 {

	List<Long> saveAll(Long studyId, List<Long> studyTagIds);

	List<StudyId> saveAll2(StudyId studyId, List<StudyId> studyTagIds);

	void remove(Long studyId, LocalDateTime now);
}
