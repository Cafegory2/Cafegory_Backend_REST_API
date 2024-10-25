package com.example.demo.helper;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.cafe.infrastructure.ReviewEntity;
import org.springframework.transaction.annotation.Transactional;


import com.example.demo.factory.TestReviewFactory;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.cafe.infrastructure.ReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class ReviewSaveHelper {

	private final ReviewRepository reviewRepository;
	private final CafeRepository cafeRepository;
	private final MemberRepository memberRepository;

	public ReviewEntity saveReview(CafeEntity cafe, MemberEntity member) {
		CafeEntity mergedCafe = cafeRepository.save(cafe);
		MemberEntity mergedMember = memberRepository.save(member);
		ReviewEntity review = TestReviewFactory.createReview(mergedCafe, mergedMember);
		return reviewRepository.save(review);
	}
}
