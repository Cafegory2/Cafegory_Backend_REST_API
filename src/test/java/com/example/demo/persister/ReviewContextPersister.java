package com.example.demo.persister;

import static com.example.demo.persister.ReviewCafeTagPersister.*;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.db.cafe.cafe.CafeEntity;
import com.example.demo.db.cafe.review.ReviewEntity;
import com.example.demo.db.cafe.review.ReviewJpaRepository;
import com.example.demo.db.cafe.tag.CafeTagEntity;
import com.example.demo.db.member.MemberEntity;

public class ReviewContextPersister {

	private CafeEntity cafe;
	private MemberEntity member;

	private List<CafeTagEntity> cafeTags = new ArrayList<>();

	private ReviewContextPersister() {
	}

	private ReviewContextPersister(ReviewContextPersister copy) {
		this.cafe = copy.cafe;
		this.member = copy.member;
		this.cafeTags = copy.cafeTags;
	}

	public ReviewContextPersister but() {
		return new ReviewContextPersister(this);
	}

	public static ReviewContextPersister aReview() {
		return new ReviewContextPersister();
	}

	public ReviewContextPersister withCafe(CafeEntity cafe) {
		this.cafe = cafe;
		return this;
	}

	public ReviewContextPersister withMember(MemberEntity member) {
		this.member = member;
		return this;
	}

	public ReviewContextPersister includeTags(CafeTagEntity... cafeTags) {
		this.cafeTags.addAll(List.of(cafeTags));
		return this;
	}

	public ReviewEntity build() {
		return ReviewEntity.builder()
			.cafe(this.cafe)
			.member(this.member)
			.build();
	}

	public static class ReviewRepoHolder {
		private static ReviewJpaRepository reviewJpaRepository;

		public static void init(ReviewJpaRepository reviewRepo) {
			reviewJpaRepository = reviewRepo;
		}
	}

	public ReviewEntity save() {
		ReviewEntity review = ReviewRepoHolder.reviewJpaRepository.save(build());
		cafeTags.forEach(cafeTag -> aReviewCafeTag().withReview(review).withCafeTag(cafeTag).persist());

		return review;
	}
}
