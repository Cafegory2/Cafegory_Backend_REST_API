package com.example.demo.repository.study;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.implement.study.CafeStudy;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CafeStudyRepository extends JpaRepository<CafeStudy, Long> {

	@Query(value = "select s from CafeStudy s" +
		" inner join fetch s.coordinator")
	List<CafeStudy> findAllByCafeId(Long cafeId);
}
