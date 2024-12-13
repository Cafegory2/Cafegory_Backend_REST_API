package com.example.demo.study.infrastructure.repository2;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.domain.StudyTag;

public interface StudyTagRepository {

	List<StudyTag> findByTags(@Param("tags") List<CafeStudyTagType> tags);

}
