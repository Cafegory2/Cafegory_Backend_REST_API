package com.example.demo.persister;

import com.example.demo.db.cafe.review.ReviewCafeTagEntity;
import com.example.demo.db.cafe.review.ReviewCafeTagJpaRepository;
import com.example.demo.db.cafe.review.ReviewEntity;
import com.example.demo.db.cafe.tag.CafeTagEntity;

public class ReviewCafeTagPersister {

	private ReviewEntity review;
	private CafeTagEntity cafeTag;

	private ReviewCafeTagPersister() {
	}

	private ReviewCafeTagPersister(ReviewCafeTagPersister copy) {
		this.review = copy.review;
		this.cafeTag = copy.cafeTag;
	}

	public ReviewCafeTagPersister but() {
		return new ReviewCafeTagPersister(this);
	}

	public static ReviewCafeTagPersister aReviewCafeTag() {
		return new ReviewCafeTagPersister();
	}

	public ReviewCafeTagPersister withReview(ReviewEntity review) {
		this.review = review;
		return this;
	}

	public ReviewCafeTagPersister withCafeTag(CafeTagEntity cafeTag) {
		this.cafeTag = cafeTag;
		return this;
	}

	public ReviewCafeTagEntity build() {
		return ReviewCafeTagEntity.builder()
			.review(this.review)
			.cafeTag(this.cafeTag)
			.build();
	}

	public static class ReviewCafeTagRepoHolder {
		private static ReviewCafeTagJpaRepository reviewCafeTagJpaRepository;

		public static void init(ReviewCafeTagJpaRepository reviewCafeTagRepo) {
			reviewCafeTagJpaRepository = reviewCafeTagRepo;
		}
	}

	public ReviewCafeTagEntity persist() {
		return ReviewCafeTagRepoHolder.reviewCafeTagJpaRepository.save(build());
	}
}
