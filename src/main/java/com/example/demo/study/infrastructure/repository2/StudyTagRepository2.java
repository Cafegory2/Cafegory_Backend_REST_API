package com.example.demo.study.infrastructure.repository2;

import java.util.List;

import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyTag;

public interface StudyTagRepository2 {

	List<StudyTag> findByTags(List<CafeStudyTagType> tags);

	List<Long> countByTags(List<CafeStudyTagType> tags);

	List<StudyId> countByTags2(List<CafeStudyTagType> tags);
}
