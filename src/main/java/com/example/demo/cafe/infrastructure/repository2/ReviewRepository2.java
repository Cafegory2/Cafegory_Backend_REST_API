package com.example.demo.cafe.infrastructure.repository2;

import com.example.demo.cafe.domain.Review;

import java.util.List;

public interface ReviewRepository2 {

    List<Review> findAllByMemberId(Long memberId);
}
