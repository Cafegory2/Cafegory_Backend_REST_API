package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.ReviewImpl;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewSearchResponse;
import com.example.demo.dto.WriterResponse;

public class ReviewMapper {

	public List<ReviewSearchResponse> entitiesToReviewSearchResponses(List<ReviewImpl> reviews) {
		return reviews.stream()
			.map(review ->
				new ReviewSearchResponse(
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

	// public List<ReviewSearchResponse> pagedEntityToReviewSearchResponses(Page<ReviewImpl> pagedReviews) {
	// 	return pagedReviews.getContent().stream()
	// 		.map(review ->
	// 			new ReviewSearchResponse(
	// 				review.getId(),
	// 				produceWriterResponse(
	// 					review.getMember().getId(), review.getMember().getName(),
	// 					review.getMember().getThumbnailImage().getThumbnailImage()
	// 				),
	// 				review.getRate(),
	// 				review.getContent()
	// 			)
	// 		)
	// 		.collect(Collectors.toList());
	// }

	private WriterResponse produceWriterResponse(Long memberId, String name, String thumbnailImg) {
		return new WriterResponse(memberId, name, thumbnailImg);
	}

	public ReviewResponse entityToReviewResponse(ReviewImpl findReview) {
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

	public List<ReviewResponse> entitiesToReviewResponses(List<ReviewImpl> reviews) {
		return reviews.stream()
			.map(review ->
					produceReviewResponse(
						review.getId(),
						memberEntityToWriterResponse(review.getMember()),
						review.getRate(),
						review.getContent()
					)
				// ReviewResponse.builder()
				// 	.reviewId(review.getId())
				// 	.writer(
				// 		new WriterResponse(review.getMember().getId(),
				// 			review.getMember().getName(),
				// 			review.getMember().getThumbnailImage().getThumbnailImage()
				// 		))
				// 	.rate(review.getRate())
				// 	.content(review.getContent())
				// 	.build()
			)
			.collect(Collectors.toList());
	}

	private ReviewResponse produceReviewResponse(Long reviewId, WriterResponse writerResponse, double rate,
		String content) {
		return ReviewResponse.builder()
			.reviewId(reviewId)
			.writer(writerResponse)
			.rate(rate)
			.content(content)
			.build();
	}

	private WriterResponse memberEntityToWriterResponse(MemberImpl member) {
		return new WriterResponse(member.getId(),
			member.getName(),
			member.getThumbnailImage().getThumbnailImage()
		);
	}

}
