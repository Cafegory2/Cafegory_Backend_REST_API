package com.example.demo.db.cafe.review;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.cafe.domain.Review;
import com.example.demo.domain.cafe.repository.ReviewRepository;
import com.example.demo.domain.member.domain.MemberId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

	private final ReviewJpaRepository reviewJpaRepository;

	@Override
	public List<Review> findAllByMemberId(MemberId memberId) {
		List<ReviewEntity> reviewEntities = reviewJpaRepository.findAllByMemberId(memberId.getId());

		return reviewEntities.stream()
			.map(ReviewEntity::toReview)
			.collect(Collectors.toList());
	}
}
