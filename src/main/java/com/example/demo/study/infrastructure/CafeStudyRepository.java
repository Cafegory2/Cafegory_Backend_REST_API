package com.example.demo.study.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CafeStudyRepository extends JpaRepository<CafeStudyEntity, Long> {

	@Query(value = "select s from CafeStudyEntity s" +
		" inner join fetch s.coordinator")
	List<CafeStudyEntity> findAllByCafeId(Long cafeId);
}
