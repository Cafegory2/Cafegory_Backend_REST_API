package com.example.demo.domain.cafe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.cafe.domain.Review;
import com.example.demo.domain.cafe.implement.ReviewReader;
import com.example.demo.domain.member.domain.MemberId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewReader reviewReader;

	public List<Review> getReviews(MemberId memberId) {
		return reviewReader.readBy(memberId);
	}
}
