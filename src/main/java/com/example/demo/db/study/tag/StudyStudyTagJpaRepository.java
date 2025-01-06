package com.example.demo.db.study.tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyStudyTagJpaRepository extends JpaRepository<CafeStudyCafeStudyTagEntity, Long> {

	List<CafeStudyCafeStudyTagEntity> findByCafeStudy_Id(Long studyId);
}
