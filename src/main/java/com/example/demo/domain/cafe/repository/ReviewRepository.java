package com.example.demo.domain.cafe.repository;

import java.util.List;

import com.example.demo.domain.cafe.domain.Review;
import com.example.demo.domain.member.domain.MemberId;

public interface ReviewRepository {

	List<Review> findAllByMemberId(MemberId memberId);
}
