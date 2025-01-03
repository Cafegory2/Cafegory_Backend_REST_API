package com.example.demo.cafe.infrastructure.repository2;

import com.example.demo.cafe.domain.Review;
import com.example.demo.member.domain.MemberId;

import java.util.List;

public interface ReviewRepository2 {

    List<Review> findAllByMemberId(MemberId memberId);
}
