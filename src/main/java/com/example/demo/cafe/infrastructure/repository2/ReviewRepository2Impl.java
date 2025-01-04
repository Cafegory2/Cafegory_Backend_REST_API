package com.example.demo.cafe.infrastructure.repository2;

import com.example.demo.cafe.domain.Review;
import com.example.demo.cafe.infrastructure.ReviewEntity;
import com.example.demo.cafe.infrastructure.ReviewRepository;
import com.example.demo.member.domain.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReviewRepository2Impl implements ReviewRepository2 {

    private final ReviewRepository reviewJpaRepository;

    @Override
    public List<Review> findAllByMemberId(MemberId memberId) {
        List<ReviewEntity> reviewEntities = reviewJpaRepository.findAllByMemberId(memberId.getId());

        return reviewEntities.stream()
            .map(ReviewEntity::toReview)
            .collect(Collectors.toList());
    }
}
