package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyStudyTagId;
import com.example.demo.study.domain.StudyTagId;

public interface StudyStudyTagRepository2 {

	List<StudyStudyTagId> saveAll(StudyId studyId, List<StudyTagId> studyTagIds);

	void remove(StudyId studyId, LocalDateTime now);

}
