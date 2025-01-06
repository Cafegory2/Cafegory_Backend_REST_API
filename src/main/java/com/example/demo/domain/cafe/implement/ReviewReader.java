package com.example.demo.domain.cafe.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.domain.cafe.repository.ReviewRepository;
import com.example.demo.domain.cafe.domain.Review;
import com.example.demo.domain.member.domain.MemberId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewReader {

	private final ReviewRepository reviewRepository;

	public List<Review> readBy(MemberId memberId) {
		return reviewRepository.findAllByMemberId(memberId);
	}
}
