package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.example.demo.study.domain.Study;

public interface StudyQueryRepository2 {

	Study findWithMember(@Param("studyId") Long studyId);

	List<Study> findAllByCafeId(Long cafeId);

	List<Study> findUpcomingsWithMemberBy(@Param("studyIds") List<Long> studyIds, @Param("now") LocalDateTime now);

}
