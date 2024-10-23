package com.example.demo.repository.study;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.implement.study.CafeStudyEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CafeStudyRepository extends JpaRepository<CafeStudyEntity, Long> {

	@Query(value = "select s from CafeStudyEntity s" +
		" inner join fetch s.coordinator")
	List<CafeStudyEntity> findAllByCafeId(Long cafeId);
}
