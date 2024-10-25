package com.example.demo.repository.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.implement.review.ReviewEntity;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

	@Query("select distinct r from ReviewEntity r" +
		" inner join fetch r.cafe" +
		" left join fetch r.reviewCafeTag")
	List<ReviewEntity> findAllByMemberId(Long memberId);
}
