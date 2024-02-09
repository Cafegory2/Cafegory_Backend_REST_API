package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.ReviewImpl;

public interface ReviewRepository extends JpaRepository<ReviewImpl, Long> {

	@Query("select r from ReviewImpl r join fetch r.member where r.cafe.id = :cafeId")
	List<ReviewImpl> findAllByCafeId(@Param("cafeId") Long cafeId);
}
