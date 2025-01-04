package com.example.demo.cafe.implement;

import java.util.List;

import com.example.demo.cafe.domain.Review;
import com.example.demo.cafe.infrastructure.repository2.ReviewRepository2;
import com.example.demo.member.domain.MemberId;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewReader {

	private final ReviewRepository2 reviewRepository2;

	public List<Review> readBy(MemberId memberId) {
		return reviewRepository2.findAllByMemberId(memberId);
	}
}
