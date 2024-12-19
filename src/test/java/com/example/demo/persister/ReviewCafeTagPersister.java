package com.example.demo.persister;

import com.example.demo.cafe.infrastructure.CafeTagEntity;
import com.example.demo.cafe.infrastructure.ReviewCafeTagEntity;
import com.example.demo.cafe.infrastructure.ReviewCafeTagRepository;
import com.example.demo.cafe.infrastructure.ReviewEntity;

public class ReviewCafeTagPersister {

    private ReviewEntity review;
    private CafeTagEntity cafeTag;

    private ReviewCafeTagPersister() {}

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
        private static ReviewCafeTagRepository reviewCafeTagRepository;

        public static void init(ReviewCafeTagRepository reviewCafeTagRepo) {
            reviewCafeTagRepository = reviewCafeTagRepo;
        }
    }

    public ReviewCafeTagEntity save() {
        return ReviewCafeTagRepoHolder.reviewCafeTagRepository.save(build());
    }
}
