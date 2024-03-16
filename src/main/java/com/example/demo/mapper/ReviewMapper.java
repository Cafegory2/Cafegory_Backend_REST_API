package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.example.demo.domain.ReviewImpl;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewSearchResponse;
import com.example.demo.dto.WriterResponse;

public class ReviewMapper {

	public List<ReviewSearchResponse> pagedReviewsToReviewSearchResponses(Page<ReviewImpl> pagedReviews) {
		return pagedReviews.getContent().stream()
			.map(review ->
				new ReviewSearchResponse(
					review.getId(),
					produceWriterResponse(
						review.getMember().getId(), review.getMember().getName(),
						review.getMember().getThumbnailImage().getThumbnailImage()
					),
					review.getRate(),
					review.getContent()
				)
			)
			.collect(Collectors.toList());
	}

	private WriterResponse produceWriterResponse(Long memberId, String name, String thumbnailImg) {
		return new WriterResponse(memberId, name, thumbnailImg);
	}

	public ReviewResponse reviewToReviewResponse(ReviewImpl findReview) {
		return ReviewResponse.builder()
			.reviewId(findReview.getId())
			.writer(
				produceWriterResponse(
					findReview.getMember().getId(),
					findReview.getMember().getName(),
					findReview.getMember().getThumbnailImage().getThumbnailImage()
				))
			.rate(findReview.getRate())
			.content(findReview.getContent())
			.build();
	}

}
