package com.example.demo.repository.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.implement.review.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

	List<ReviewEntity> findAllByMemberId(Long memberId);
	
}
