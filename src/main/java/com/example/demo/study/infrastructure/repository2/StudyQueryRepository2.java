package com.example.demo.study.infrastructure.repository2;

import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.ViewCount;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudyQueryRepository2 {

	Study findById(Long studyId);

	Optional<Study> findWithMember(Long studyId);

	Optional<ViewCount> findViewCountBy(Long studyId);

	List<Study> findUpcomingsWithMemberBy(List<Long> studyIds, LocalDateTime now);
}
