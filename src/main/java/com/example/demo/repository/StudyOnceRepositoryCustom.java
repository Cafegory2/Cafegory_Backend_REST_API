package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.dto.StudyOnceSearchRequest;

public interface StudyOnceRepositoryCustom {
	List<StudyOnceImpl> findAllByStudyOnceSearchRequest(StudyOnceSearchRequest studyOnceSearchRequest);

	Long count(StudyOnceSearchRequest studyOnceSearchRequest);

	boolean existsByLeaderIdAndStudyTime(long leaderId, LocalDateTime start, LocalDateTime end);
}
