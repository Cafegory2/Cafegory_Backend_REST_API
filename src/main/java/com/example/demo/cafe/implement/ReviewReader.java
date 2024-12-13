package com.example.demo.cafe.implement;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.cafe.domain.Review;
import com.example.demo.cafe.infrastructure.repository2.ReviewRepository2;
import org.springframework.stereotype.Component;

import com.example.demo.cafe.infrastructure.ReviewEntity;
import com.example.demo.cafe.infrastructure.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewReader {

	private final ReviewRepository2 reviewRepository2;

	public List<Review> readBy(Long memberId) {
		return reviewRepository2.findAllByMemberId(memberId);
	}
}
