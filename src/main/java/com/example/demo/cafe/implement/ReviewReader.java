package com.example.demo.cafe.implement;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.cafe.domain.Review;
import org.springframework.stereotype.Component;

import com.example.demo.cafe.infrastructure.ReviewEntity;
import com.example.demo.cafe.infrastructure.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewReader {

	private final ReviewRepository reviewRepository;

	public List<Review> readBy(Long memberId) {
		List<ReviewEntity> reviewEntities = reviewRepository.findAllByMemberId(memberId);

		return reviewEntities.stream()
			.map(ReviewEntity::toReview)
			.collect(Collectors.toList());
	}
}
