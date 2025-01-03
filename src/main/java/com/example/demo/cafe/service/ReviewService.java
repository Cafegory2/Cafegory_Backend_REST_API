package com.example.demo.cafe.service;

import com.example.demo.cafe.domain.Review;
import com.example.demo.cafe.implement.ReviewReader;
import com.example.demo.member.domain.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewReader reviewReader;

    @Transactional(readOnly = true)
    public List<Review> getReviews(MemberId memberId) {
        return reviewReader.readBy(memberId);
    }
}
