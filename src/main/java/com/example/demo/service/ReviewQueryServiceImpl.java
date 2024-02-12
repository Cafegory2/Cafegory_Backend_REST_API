package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.ReviewImpl;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewSearchRequest;
import com.example.demo.dto.ReviewSearchResponse;
import com.example.demo.dto.WriterResponse;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.util.PageRequestCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

	private final ReviewRepository reviewRepository;
	private final CafeRepository cafeRepository;

	@Override
	public PagedResponse<ReviewSearchResponse> searchWithPagingByCafeId(ReviewSearchRequest request) {
		validateExistCafe(request.getCafeId());
		Pageable pageable = PageRequestCustom.of(request.getPage(), request.getSizePerPage());
		Page<ReviewImpl> pagedReviews = reviewRepository.findAllWithPagingByCafeId(request.getCafeId(),
			pageable);
		return createPagedResponse(pagedReviews, mapToResponseList(pagedReviews));
	}

	@Override
	public ReviewResponse searchOne(Long reviewId) {
		return mapToReviewResponse(findReviewById(reviewId));
	}

	private ReviewImpl findReviewById(Long reviewId) {
		return reviewRepository.findById(reviewId)
			.orElseThrow(() -> new IllegalArgumentException("없는 리뷰입니다."));
	}

	private ReviewResponse mapToReviewResponse(ReviewImpl findReview) {
		return ReviewResponse.builder()
			.id(findReview.getId())
			.writer(
				new WriterResponse(findReview.getMember().getId(),
					findReview.getMember().getName(),
					findReview.getMember().getThumbnailImage().getThumbnailImage()
				))
			.rate(findReview.getRate())
			.content(findReview.getContent())
			.build();
	}

	private void validateExistCafe(Long cafeId) {
		if (!cafeRepository.existsById(cafeId)) {
			throw new IllegalArgumentException("없는 카페입니다.");
		}
	}

	private PagedResponse<ReviewSearchResponse> createPagedResponse(Page<ReviewImpl> pagedReviews,
		List<ReviewSearchResponse> reviewSearchResponse) {
		return PagedResponse.createWithFirstPageAsOne(
			pagedReviews.getNumber(),
			pagedReviews.getTotalPages(),
			pagedReviews.getNumberOfElements(),
			reviewSearchResponse
		);
	}

	private List<ReviewSearchResponse> mapToResponseList(Page<ReviewImpl> pagedReviews) {
		return pagedReviews.getContent().stream()
			.map(review ->
				new ReviewSearchResponse(
					review.getId(),
					new WriterResponse(review.getMember().getId(), review.getMember().getName(),
						review.getMember().getThumbnailImage().getThumbnailImage()),
					review.getRate(),
					review.getContent()
				)
			)
			.collect(Collectors.toList());
	}
}
