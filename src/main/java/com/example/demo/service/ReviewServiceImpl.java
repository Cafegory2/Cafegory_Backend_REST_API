package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.ReviewImpl;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.ReviewSearchRequest;
import com.example.demo.dto.ReviewSearchResponse;
import com.example.demo.dto.WriterResponse;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.util.PageRequestCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final CafeRepository cafeRepository;

	public PagedResponse<ReviewSearchResponse> searchWithPagingByCafeId(ReviewSearchRequest request) {
		validateExistCar(request.getCafeId());
		Pageable pageable = PageRequestCustom.of(request.getPage(), request.getSizePerPage());
		Page<ReviewImpl> pagedReviews = reviewRepository.findAllWithPagingByCafeId(request.getCafeId(),
			pageable);
		return createPagedResponse(pagedReviews, mapToResponseList(pagedReviews));
	}

	private void validateExistCar(Long cafeId) {
		cafeRepository.findById(cafeId)
			.orElseThrow(() -> new IllegalArgumentException("없는 카페입니다."));
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


