package com.example.demo.db.study.repository2;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.domain.study.domain.StudyId;
import com.example.demo.domain.study.domain.StudyStudyTagId;
import com.example.demo.domain.study.domain.StudyTagId;

public interface StudyStudyTagRepository {

	List<StudyStudyTagId> saveAll(StudyId studyId, List<StudyTagId> studyTagIds);

	void remove(StudyId studyId, LocalDateTime now);

}
