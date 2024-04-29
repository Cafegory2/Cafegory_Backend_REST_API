package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.review.Review;
import com.example.demo.dto.WriterResponse;
import com.example.demo.dto.cafe.CafeSearchReviewResponse;
import com.example.demo.dto.cafe.CafeSearchWriterResponse;
import com.example.demo.dto.review.ReviewResponse;
import com.example.demo.dto.review.ReviewSearchListResponse;

public class ReviewMapper {

	public List<ReviewSearchListResponse> toReviewSearchListResponses(List<Review> reviews) {
		return reviews.stream()
			.map(review ->
				new ReviewSearchListResponse(
					review.getId(),
					produceWriterResponse(
						review.getMember().getId(), review.getMember().getName(),
						review.getMember().getThumbnailImage().getThumbnailImage()
					),
					review.getRate(),
					review.getContent()
				))
			.collect(Collectors.toList());
	}

	private WriterResponse produceWriterResponse(Long memberId, String name, String thumbnailImg) {
		return new WriterResponse(memberId, name, thumbnailImg);
	}

	public ReviewResponse toReviewResponse(Review findReview) {
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

	public List<CafeSearchReviewResponse> toCafeSearchReviewResponses(List<Review> reviews) {
		return reviews.stream()
			.map(review ->
				makeCafeSearchReviewResponse(
					review.getId(),
					makeCafeSearchWriterResponse(review.getMember()),
					review.getRate(),
					review.getContent()
				)
			)
			.collect(Collectors.toList());
	}

	private CafeSearchReviewResponse makeCafeSearchReviewResponse(Long reviewId,
		CafeSearchWriterResponse writerResponse,
		double rate,
		String content) {
		return CafeSearchReviewResponse.builder()
			.reviewId(reviewId)
			.writer(writerResponse)
			.rate(rate)
			.content(content)
			.build();
	}

	private CafeSearchWriterResponse makeCafeSearchWriterResponse(Member member) {
		return new CafeSearchWriterResponse(member.getId(),
			member.getName(),
			member.getThumbnailImage().getThumbnailImage()
		);
	}

}
