package com.example.demo.persister;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeTagEntity;
import com.example.demo.cafe.infrastructure.ReviewEntity;
import com.example.demo.cafe.infrastructure.ReviewRepository;
import com.example.demo.member.infrastructure.MemberEntity;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.persister.ReviewCafeTagPersister.*;

public class ReviewContextPersister {

    private CafeEntity cafe;
    private MemberEntity member;

    private List<CafeTagEntity> cafeTags = new ArrayList<>();

    private ReviewContextPersister() {}

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
        private static ReviewRepository reviewRepository;

        public static void init(ReviewRepository reviewRepo) {
            reviewRepository = reviewRepo;
        }
    }

    public ReviewEntity save() {
        ReviewEntity review = ReviewRepoHolder.reviewRepository.save(build());
        cafeTags.forEach(cafeTag -> aReviewCafeTag().withReview(review).withCafeTag(cafeTag).save());

        return review;
    }
}
