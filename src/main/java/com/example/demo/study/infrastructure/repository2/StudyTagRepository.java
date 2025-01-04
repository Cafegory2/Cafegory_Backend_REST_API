package com.example.demo.study.infrastructure.repository2;

import java.util.List;

import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.domain.StudyTagId;

public interface StudyTagRepository {

	List<StudyTagId> countByTags(List<CafeStudyTagType> tags);
}
