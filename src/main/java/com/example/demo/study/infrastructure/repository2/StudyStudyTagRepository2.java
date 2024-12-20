package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;
import java.util.List;

public interface StudyStudyTagRepository2 {

	List<Long> saveAll(Long studyId, List<Long> studyTagIds);

	void remove(Long studyId, LocalDateTime now);
}
