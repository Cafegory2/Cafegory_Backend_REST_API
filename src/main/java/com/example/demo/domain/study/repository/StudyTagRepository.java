package com.example.demo.domain.study.repository;

import java.util.List;

import com.example.demo.domain.study.domain.CafeStudyTagType;
import com.example.demo.domain.study.domain.StudyTagId;

public interface StudyTagRepository {

	List<StudyTagId> countByTags(List<CafeStudyTagType> tags);
}
