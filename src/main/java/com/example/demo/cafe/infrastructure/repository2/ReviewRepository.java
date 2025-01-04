package com.example.demo.cafe.infrastructure.repository2;

import java.util.List;

import com.example.demo.cafe.domain.Review;
import com.example.demo.member.domain.MemberId;

public interface ReviewRepository {

	List<Review> findAllByMemberId(MemberId memberId);
}
