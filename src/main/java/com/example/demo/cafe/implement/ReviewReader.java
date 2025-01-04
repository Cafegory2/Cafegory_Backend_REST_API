package com.example.demo.cafe.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.cafe.domain.Review;
import com.example.demo.cafe.infrastructure.repository2.ReviewRepository;
import com.example.demo.member.domain.MemberId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewReader {

	private final ReviewRepository reviewRepository;

	public List<Review> readBy(MemberId memberId) {
		return reviewRepository.findAllByMemberId(memberId);
	}
}
