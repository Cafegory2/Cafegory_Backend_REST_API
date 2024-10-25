package com.example.demo.service.review;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.implement.review.ReviewEntity;
import com.example.demo.repository.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewReader {

	private final ReviewRepository reviewRepository;

	public List<ReviewEntity> readBy(Long memberId) {
		return reviewRepository.findAllByMemberId(memberId);
	}
}
