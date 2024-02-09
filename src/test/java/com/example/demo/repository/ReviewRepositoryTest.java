package com.example.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.QueryDslConfig;
import com.example.demo.domain.Address;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MaxAllowableStay;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.ReviewImpl;
import com.example.demo.domain.ThumbnailImage;

@DataJpaTest
@Import({QueryDslConfig.class})
@Transactional
class ReviewRepositoryTest {

	@Autowired
	private EntityManager em;
	@Autowired
	private ReviewRepository reviewRepository;

	@Test
	void findAllByCafeId() {
		CafeImpl cafe = CafeImpl.builder()
			.name("카페고리")
			.address(new Address("서울 마포구 합정동", "합정동"))
			.phone("010-1234-5678")
			.maxAllowableStay(MaxAllowableStay.FIVE_HOUR)
			.isAbleToStudy(true)
			.minBeveragePrice(3_000)
			.build();
		em.persist(cafe);

		ThumbnailImage thumb = ThumbnailImage.builder()
			.thumbnailImage("썸네일")
			.build();
		em.persist(thumb);

		MemberImpl member = MemberImpl.builder()
			.name("김동현")
			.email("aaaa@naver.com")
			.thumbnailImage(thumb)
			.build();
		em.persist(member);

		ReviewImpl review1 = ReviewImpl.builder()
			.content("커피가 맛있어요")
			.rate(4.9)
			.cafe(cafe)
			.member(member)
			.build();
		ReviewImpl review2 = ReviewImpl.builder()
			.content("주차장이 없어서 불편했어요")
			.rate(2)
			.cafe(cafe)
			.member(member)
			.build();
		em.persist(review1);
		em.persist(review2);

		em.flush();
		em.clear();

		List<ReviewImpl> findCafes = reviewRepository.findAllByCafeId(cafe.getId());
		Assertions.assertThat(findCafes.size()).isEqualTo(2);
	}

}