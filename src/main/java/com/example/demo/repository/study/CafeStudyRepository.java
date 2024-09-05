package com.example.demo.repository.study;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.study.CafeStudy;

public interface CafeStudyRepository extends JpaRepository<CafeStudy, Long>, CafeStudyRepositoryCustom {

	// boolean existsByLeaderId(Long leaderId);

	// List<CafeStudy> findByLeaderId(Long leaderId);
}
