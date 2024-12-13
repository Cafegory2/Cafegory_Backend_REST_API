package com.example.demo.cafe.infrastructure.repository2;

import com.example.demo.cafe.domain.Review;
import com.example.demo.cafe.infrastructure.ReviewEntity;
import com.example.demo.cafe.infrastructure.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReviewRepository2Impl implements ReviewRepository2 {

    private final ReviewRepository reviewJpaRepository;

    @Override
    public List<Review> findAllByMemberId(Long memberId) {
        List<ReviewEntity> reviewEntities = reviewJpaRepository.findAllByMemberId(memberId);

        return reviewEntities.stream()
            .map(ReviewEntity::toReview)
            .collect(Collectors.toList());
    }
}
