package com.example.demo.repository.study;

import java.util.List;

import com.example.demo.domain.study.CafeStudy;
import com.example.demo.dto.study.StudyOnceSearchRequest;

public interface StudyOnceRepositoryCustom {
	List<CafeStudy> findAllByStudyOnceSearchRequest(StudyOnceSearchRequest studyOnceSearchRequest);

	Long count(StudyOnceSearchRequest studyOnceSearchRequest);
}
