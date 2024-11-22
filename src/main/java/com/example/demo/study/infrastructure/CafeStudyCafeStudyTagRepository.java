package com.example.demo.study.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeStudyCafeStudyTagRepository extends JpaRepository<CafeStudyCafeStudyTagEntity, Long> {

	List<CafeStudyCafeStudyTagEntity> findByCafeStudy_Id(Long studyId);
}
