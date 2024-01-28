package com.example.demo.repository.cafe;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Address;
import com.example.demo.domain.BusinessHour;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.Menu;
import com.example.demo.domain.ReviewImpl;
import com.example.demo.domain.SnsDetail;
import com.example.demo.service.dto.CafeSearchCondition;
import com.example.demo.util.PageRequestCustom;

// @DataJpaTest
@SpringBootTest
@Profile("test")
@Transactional
	// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CafeRepositorySearchMethodTest {

	@Autowired
	private EntityManager em;
	@Autowired
	private CafeQueryRepository cafeRepository;
	// private CafeRepository cafeRepository;

	void setUp(CafeSearchCondition searchCondition) {

		for (int i = 0; i < 20; i++) {
			CafeImpl cafe = CafeImpl.builder()
				.name("카페고리" + i)
				.address(new Address("서울 마포구 " + searchCondition.getRegion(), searchCondition.getRegion()))
				.phone("010-1234-5678")
				.maxAllowableStay(searchCondition.getMaxAllowableStay())
				.isAbleToStudy(searchCondition.isAbleToStudy())
				.minMenuPrice(searchCondition.getMinMenuPrice())
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

	private CafeSearchCondition createSearchConditionByRequirements(boolean isAbleToStudy, String region) {
		return new CafeSearchCondition.Builder(isAbleToStudy, region)
			.build();
	}

	@Test
	@DisplayName("데이터가 없으면 빈값을 반환한다")
	void search_Cafes_When_No_Data_Then_EmptyList() {
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilterAndNoPaging(
			createSearchConditionByRequirements(true, "상수동"));
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
	void search_Cafes_Filtering_With_CanStudy_When_Exists_CanStudyCafe() {
		//given

		setUp(createSearchConditionByRequirements(true, "상수동"));
		CafeSearchCondition searchCondition = createSearchConditionByRequirements(true, "상수동");
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(20);
	}

	@Test
	@DisplayName("공부가 가능한 카페가 존재할때, 공부가 불가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanNotStudy_When_Exists_CanStudyCafe() {
		//given
		setUp(createSearchConditionByRequirements(true, "상수동"));
		CafeSearchCondition searchCondition = createSearchConditionByRequirements(false, "상수동");
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("공부가 가능한 카페가 존재하지 않을때, 공부가 가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanStudy_When_Not_Exists_CanStudyCafe() {
		//given
		setUp(createSearchConditionByRequirements(false, "상수동"));
		CafeSearchCondition searchCondition = createSearchConditionByRequirements(true, "상수동");
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("공부가 가능한 카페가 존재하지 않을때, 공부가 불가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanNotStudy_When_Not_Exists_CanStudyCafe() {
		//given
		setUp(createSearchConditionByRequirements(false, "상수동"));
		CafeSearchCondition searchCondition = createSearchConditionByRequirements(false, "상수동");
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(20);
	}

	@Test
	@DisplayName("공부가 가능한 카페와 불가능한 카페가 존재할때, 공부가 불가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanNotStudy_When_Exists_Both() {
		//given
		setUp(createSearchConditionByRequirements(true, "상수동"));
		setUp(createSearchConditionByRequirements(false, "상수동"));
		CafeSearchCondition searchCondition = createSearchConditionByRequirements(false, "상수동");
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(20);
	}

	@Test
	@DisplayName("공부가 가능한 카페와 불가능한 카페가 존재할때, 공부가 가능한 카페로 필터링 조회")
	void search_Cafes_Filtering_With_CanStudy_When_Exists_Both() {
		//given
		setUp(createSearchConditionByRequirements(true, "상수동"));
		setUp(createSearchConditionByRequirements(false, "상수동"));
		CafeSearchCondition searchCondition = createSearchConditionByRequirements(true, "상수동");
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(20);
	}

	@Test
	@DisplayName("행정동으로 필터링, 조건에 맞는 데이터가 존재.")
	void search_Cafes_Filtering_With_Region() {
		//given
		setUp(createSearchConditionByRequirements(true, "상수동"));
		setUp(createSearchConditionByRequirements(true, "합정동"));

		CafeSearchCondition searchCondition = createSearchConditionByRequirements(true, "상수동");
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(20);
	}

	@Test
	@DisplayName("일부 문자열만 입력된 행정동으로 필터링, 조건에 맞는 데이터가 존재.")
	void search_Cafes_Filtering_With_Like_Region() {
		//given
		setUp(createSearchConditionByRequirements(true, "상수동"));
		setUp(createSearchConditionByRequirements(true, "합정동"));

		CafeSearchCondition searchCondition = createSearchConditionByRequirements(true, "상수");
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(20);
	}

	@Test
	@DisplayName("존재하지 않는 행정동으로 필터링, 데이터가 존재하지 않음")
	void search_Cafes_Filtering_With_Invalid_Region_Then_NO_Data() {
		//given
		setUp(createSearchConditionByRequirements(true, "상수동"));

		CafeSearchCondition searchCondition = createSearchConditionByRequirements(true, "쌍수100동");
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition);
		//then
		assertThat(cafes.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("whiteSpace, 공백문자, null인 행정동으로 필터링하면 필터링이 되지 않는다.")
	void search_Cafes_Filtering_With_Blank_Region_Then_No_Filtering() {
		setUp(createSearchConditionByRequirements(true, "상수동"));
		setUp(createSearchConditionByRequirements(true, "합정동"));

		//given
		CafeSearchCondition searchCondition1 = createSearchConditionByRequirements(true, null);
		//when
		List<CafeImpl> cafes1 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition1);
		//then
		assertThat(cafes1.size()).isEqualTo(40);

		//given
		CafeSearchCondition searchCondition2 = createSearchConditionByRequirements(true, "");
		//when
		List<CafeImpl> cafes2 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition2);
		//then
		assertThat(cafes2.size()).isEqualTo(40);

		//given
		CafeSearchCondition searchCondition3 = createSearchConditionByRequirements(true, " ");
		//when
		List<CafeImpl> cafes3 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition3);
		//then
		assertThat(cafes3.size()).isEqualTo(40);
	}

	private CafeSearchCondition createSearchConditionByMaxTime(boolean isAbleToStudy, String region, int maxTime) {
		return new CafeSearchCondition.Builder(isAbleToStudy, region)
			.maxTime(maxTime)
			.build();
	}

	@Test
	@DisplayName("최대 이용 가능시간으로 필터링")
	void search_Cafes_Filtering_With_maxAllowableStay() {
		setUp(createSearchConditionByMaxTime(true, "상수동", 1));
		setUp(createSearchConditionByMaxTime(true, "상수동", 2));
		setUp(createSearchConditionByMaxTime(true, "상수동", 7));

		//given
		CafeSearchCondition searchCondition1 = createSearchConditionByMaxTime(true, "상수동", 1);
		//when
		List<CafeImpl> cafes1 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition1);
		//then
		assertThat(cafes1.size()).isEqualTo(20);

		//given
		CafeSearchCondition searchCondition2 = createSearchConditionByMaxTime(true, "상수동", 2);
		//when
		List<CafeImpl> cafes2 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition2);
		//then
		assertThat(cafes2.size()).isEqualTo(40);

		//given
		CafeSearchCondition searchCondition3 = createSearchConditionByMaxTime(true, "상수동", 0);
		//when
		List<CafeImpl> cafes3 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition3);
		//then
		assertThat(cafes3.size()).isEqualTo(60);

		//given
		CafeSearchCondition searchCondition4 = createSearchConditionByMaxTime(true, "상수동", 7);
		//when
		List<CafeImpl> cafes4 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition4);
		//then
		assertThat(cafes4.size()).isEqualTo(60);

		//given
		CafeSearchCondition searchCondition5 = createSearchConditionByMaxTime(true, "상수동", 6);
		//when
		List<CafeImpl> cafes5 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition5);
		//then
		assertThat(cafes5.size()).isEqualTo(40);

	}

	private CafeSearchCondition createSearchConditionByMinMenuPrice(boolean isAbleToStudy, String region,
		int minMenuPrice) {
		return new CafeSearchCondition.Builder(isAbleToStudy, region)
			.minMenuPrice(minMenuPrice)
			.build();
	}

	@Test
	@DisplayName("최소 음료금액으로 필터링")
	void search_Cafes_Filtering_With_minBeveragePrice() {
		setUp(createSearchConditionByMinMenuPrice(true, "상수동", 1));
		setUp(createSearchConditionByMinMenuPrice(true, "상수동", 2));
		setUp(createSearchConditionByMinMenuPrice(true, "상수동", 11));

		//given
		CafeSearchCondition searchCondition1 = createSearchConditionByMinMenuPrice(true, "상수동", 1);
		//when
		List<CafeImpl> cafes1 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition1);
		//then
		assertThat(cafes1.size()).isEqualTo(20);

		//given
		CafeSearchCondition searchCondition2 = createSearchConditionByMinMenuPrice(true, "상수동", 2);
		//when
		List<CafeImpl> cafes2 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition2);
		//then
		assertThat(cafes2.size()).isEqualTo(40);

		//given
		CafeSearchCondition searchCondition3 = createSearchConditionByMinMenuPrice(true, "상수동", 0);
		//when
		List<CafeImpl> cafes3 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition3);
		//then
		assertThat(cafes3.size()).isEqualTo(60);

		//given
		CafeSearchCondition searchCondition4 = createSearchConditionByMinMenuPrice(true, "상수동", 11);
		//when
		List<CafeImpl> cafes4 = cafeRepository.findWithDynamicFilterAndNoPaging(searchCondition4);
		//then
		assertThat(cafes4.size()).isEqualTo(60);
	}

	@Test
	@DisplayName("페이징 기본값")
	void search_Cafes_With_Default_Paging() {
		setUp(createSearchConditionByRequirements(true, "상수동"));
		//given
		CafeSearchCondition searchCondition = createSearchConditionByRequirements(true, "상수동");
		//when
		List<CafeImpl> cafes = cafeRepository.findWithDynamicFilter(searchCondition,
			PageRequestCustom.createByDefault());

		// for (CafeImpl cafe : cafes) {
		// 	System.out.println("cafe = " + cafe);
		// }
		//then
		assertThat(cafes.size()).isEqualTo(10);
	}

	@Test
	@DisplayName("페이징")
	void search_Cafes_With_Paging() {
		setUp(createSearchConditionByRequirements(true, "상수동"));
		setUp(createSearchConditionByRequirements(true, "상수동"));
		//given
		CafeSearchCondition searchCondition = createSearchConditionByRequirements(true, "상수동");
		//when
		List<CafeImpl> cafes1 = cafeRepository.findWithDynamicFilter(searchCondition,
			PageRequestCustom.of(1, 20));
		//then
		assertThat(cafes1.size()).isEqualTo(20);

		//when
		List<CafeImpl> cafes2 = cafeRepository.findWithDynamicFilter(searchCondition,
			PageRequestCustom.of(1, 5));
		//then
		assertThat(cafes2.size()).isEqualTo(5);

		//when
		List<CafeImpl> cafes3 = cafeRepository.findWithDynamicFilter(searchCondition,
			PageRequestCustom.of(2, 20));
		//then
		assertThat(cafes3.size()).isEqualTo(20);

		//when
		List<CafeImpl> cafes4 = cafeRepository.findWithDynamicFilter(searchCondition,
			PageRequestCustom.of(3, 20));
		//then
		assertThat(cafes4.size()).isEqualTo(0);

		//when
		List<CafeImpl> cafes5 = cafeRepository.findWithDynamicFilter(searchCondition,
			PageRequestCustom.of(1, 50));
		//then
		assertThat(cafes5.size()).isEqualTo(40);
	}

}