package com.example.demo.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Address;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MaxAllowableStay;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.ReviewImpl;
import com.example.demo.domain.ThumbnailImage;
import com.example.demo.dto.ReviewSaveRequest;
import com.example.demo.repository.ReviewRepository;

@SpringBootTest
@Transactional
class ReviewServiceTest {

	@Autowired
	private EntityManager em;

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ReviewRepository reviewRepository;

	@Test
	void saveReview() {
		ThumbnailImage thumbnailImage1 = ThumbnailImage.builder()
			.thumbnailImage("썸네일 경로")
			.build();
		em.persist(thumbnailImage1);

		MemberImpl member1 = MemberImpl.builder()
			.name("김동현")
			.thumbnailImage(thumbnailImage1)
			.build();
		em.persist(member1);

		CafeImpl cafe = CafeImpl.builder()
			.name("카페고리")
			.address(new Address("서울 마포구 합정동", "합정동"))
			.phone("010-1234-5678")
			.maxAllowableStay(MaxAllowableStay.FIVE_HOUR)
			.isAbleToStudy(true)
			.minBeveragePrice(3_000)
			.build();
		em.persist(cafe);

		em.flush();
		em.clear();

		reviewService.saveReview(member1.getId(), cafe.getId(), new ReviewSaveRequest("커피가 맛있어요", 4.5));
		List<ReviewImpl> findReviews = reviewRepository.findAllByCafeId(cafe.getId());
		Assertions.assertThat(findReviews.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("카페 아이디가 존재하지 않으면 예외가 터진다.")
	void saveReview_cafe_exception() {
		ThumbnailImage thumbnailImage1 = ThumbnailImage.builder()
			.thumbnailImage("썸네일 경로")
			.build();
		em.persist(thumbnailImage1);

		MemberImpl member1 = MemberImpl.builder()
			.name("김동현")
			.thumbnailImage(thumbnailImage1)
			.build();
		em.persist(member1);

		CafeImpl cafe = CafeImpl.builder()
			.name("카페고리")
			.address(new Address("서울 마포구 합정동", "합정동"))
			.phone("010-1234-5678")
			.maxAllowableStay(MaxAllowableStay.FIVE_HOUR)
			.isAbleToStudy(true)
			.minBeveragePrice(3_000)
			.build();
		em.persist(cafe);

		em.flush();
		em.clear();

		reviewService.saveReview(member1.getId(), cafe.getId(), new ReviewSaveRequest("커피가 맛있어요", 4.5));
		Assertions.assertThatThrownBy(() -> {
			reviewService.saveReview(member1.getId(), 10L, new ReviewSaveRequest("커피가 맛있어요", 4.5));
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("멤버 아이디가 존재하지 않으면 예외가 터진다.")
	void saveReview_member_exception() {
		ThumbnailImage thumbnailImage1 = ThumbnailImage.builder()
			.thumbnailImage("썸네일 경로")
			.build();
		em.persist(thumbnailImage1);

		MemberImpl member1 = MemberImpl.builder()
			.name("김동현")
			.thumbnailImage(thumbnailImage1)
			.build();
		em.persist(member1);

		CafeImpl cafe = CafeImpl.builder()
			.name("카페고리")
			.address(new Address("서울 마포구 합정동", "합정동"))
			.phone("010-1234-5678")
			.maxAllowableStay(MaxAllowableStay.FIVE_HOUR)
			.isAbleToStudy(true)
			.minBeveragePrice(3_000)
			.build();
		em.persist(cafe);

		em.flush();
		em.clear();

		reviewService.saveReview(member1.getId(), cafe.getId(), new ReviewSaveRequest("커피가 맛있어요", 4.5));
		Assertions.assertThatThrownBy(() -> {
			reviewService.saveReview(10L, cafe.getId(), new ReviewSaveRequest("커피가 맛있어요", 4.5));
		}).isInstanceOf(IllegalArgumentException.class);
	}

}