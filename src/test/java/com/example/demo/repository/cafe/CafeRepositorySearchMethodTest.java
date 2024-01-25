package com.example.demo.repository.cafe;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.CafeSearchCondition;
import com.example.demo.domain.Address;
import com.example.demo.domain.BusinessHour;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.Menu;
import com.example.demo.domain.ReviewImpl;
import com.example.demo.domain.SnsDetail;

@DataJpaTest
@Profile("test")
@Transactional
	// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CafeRepositorySearchMethodTest {

	@Autowired
	private EntityManager em;
	@Autowired
	private CafeRepository cafeRepository;

	void setUp(CafeSearchCondition searchCondition) {

		for (int i = 0; i < 20; i++) {
			CafeImpl cafe = CafeImpl.builder()
				.name("카페고리" + i)
				.address(new Address("서울 마포구 상수동", "상수동"))
				.phone("010-1234-5678")
				.maxAllowableStay(3)
				.isAbleToStudy(searchCondition.isAbleToStudy())
				.build();
			em.persist(cafe);

			BusinessHour monday = new BusinessHour("월", LocalTime.of(9, 0), LocalTime.of(21, 0));
			cafe.addBusinessHour(monday);
			BusinessHour tuesday = new BusinessHour("화", LocalTime.of(9, 0), LocalTime.of(21, 0));
			cafe.addBusinessHour(tuesday);
			em.persist(monday);
			em.persist(tuesday);

			SnsDetail instagram = new SnsDetail("인스타그램", "https://www.instagram.com/cafegory/" + i);
			cafe.addSnsDetail(instagram);
			em.persist(instagram);

			MemberImpl member1 = new MemberImpl("김동현");
			em.persist(member1);
			MemberImpl member2 = new MemberImpl("임수빈");
			em.persist(member2);

			ReviewImpl review1 = new ReviewImpl("카페가 너무 이뻐요", 5, cafe, member1);
			cafe.addReview(review1);
			ReviewImpl review2 = new ReviewImpl("콘센트가 있어서 좋아요", 4.5, cafe, member2);
			cafe.addReview(review2);
			em.persist(review1);
			em.persist(review2);

			Menu menu1 = new Menu("아메리카노", 2000);
			cafe.addMenu(menu1);
			Menu menu2 = new Menu("카페라떼", 2500);
			cafe.addMenu(menu2);
			em.persist(menu1);
			em.persist(menu2);

		}

	}

	@Test
	@DisplayName("데이터가 없으면 빈값을 반환한다")
	void search_Cafes_When_No_Data_Then_EmptyList() {
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilter(new CafeSearchCondition(true));
		assertThat(cafes).isEqualTo(Collections.emptyList());
	}

	// @Test
	// @DisplayName("데이터가 존재, 필터링 없는 카페를 조회")
	// void search_Cafes_No_Filtering_When_Exists_Data_Then_Success() {
	// 	//given
	// 	CafeSearchCondition searchCondition = new CafeSearchCondition(true);
	// 	setUp(searchCondition);
	// 	//when
	// 	List<CafeImpl> cafes = cafeRepository.findWithDynamicFilter(searchCondition);
	// 	//then
	// 	assertThat(cafes.size()).isEqualTo(20);
	// }

	@Test
	@DisplayName("공부가 가능한 카페가 존재할때, 공부가 가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanStudy_If_Exists_CanStudyCafe() {
		//given
		setUp(new CafeSearchCondition(true));
		CafeSearchCondition searchCondition = new CafeSearchCondition(true);
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilter(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(20);
	}

	@Test
	@DisplayName("공부가 가능한 카페가 존재할때, 공부가 불가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanNotStudy_If_Exists_CanStudyCafe() {
		//given
		setUp(new CafeSearchCondition(true));
		CafeSearchCondition searchCondition = new CafeSearchCondition(false);
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilter(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("공부가 가능한 카페가 존재하지 않을때, 공부가 가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanStudy_If_Not_Exists_CanStudyCafe() {
		//given
		setUp(new CafeSearchCondition(false));
		CafeSearchCondition searchCondition = new CafeSearchCondition(true);
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilter(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("공부가 가능한 카페가 존재하지 않을때, 공부가 불가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanNotStudy_If_Not_Exists_CanStudyCafe() {
		//given
		setUp(new CafeSearchCondition(false));
		CafeSearchCondition searchCondition = new CafeSearchCondition(false);
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilter(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(20);
	}

	@Test
	@DisplayName("공부가 가능한 카페와 불가능한 카페가 존재할때, 공부가 불가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanNotStudy_If_Exists_Both() {
		//given
		setUp(new CafeSearchCondition(true));
		setUp(new CafeSearchCondition(false));
		CafeSearchCondition searchCondition = new CafeSearchCondition(false);
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilter(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(20);
	}

	@Test
	@DisplayName("공부가 가능한 카페와 불가능한 카페가 존재할때, 공부가 가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanStudy_If_Exists_Both() {
		//given
		setUp(new CafeSearchCondition(true));
		setUp(new CafeSearchCondition(false));
		CafeSearchCondition searchCondition = new CafeSearchCondition(true);
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilter(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(20);
	}

	// @Test
	// void search_Cafes_Filtering_With_

}