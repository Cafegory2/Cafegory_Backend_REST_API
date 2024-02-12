package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.ReviewImpl;
import com.example.demo.dto.ReviewSaveRequest;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.cafe.CafeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

	private final CafeRepository cafeRepository;
	private final MemberRepository memberRepository;
	private final ReviewRepository reviewRepository;

	@Override
	public Long saveReview(Long memberId, Long cafeId, ReviewSaveRequest request) {
		ReviewImpl review = ReviewImpl.builder()
			.content(request.getContent())
			.rate(request.getRate())
			.cafe(findCafeById(cafeId))
			.member(findMemberById(memberId))
			.build();
		ReviewImpl savedReview = reviewRepository.save(review);
		return savedReview.getId();
	}

	private MemberImpl findMemberById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("없는 멤버입니다."));
	}

	private CafeImpl findCafeById(Long cafeId) {
		return cafeRepository.findById(cafeId)
			.orElseThrow(() -> new IllegalArgumentException("없는 카페입니다."));
	}

}


