package com.example.demo.repository.cafe;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;

import com.example.demo.domain.CafeImpl;

@DataJpaTest
@Profile("test")
class CafeRepositoryTest {

	@Autowired
	private EntityManager em;
	@Autowired
	private CafeRepository cafeRepository;

	@Test
	@DisplayName("필터링없는 카페를 조회하는데 데이터가 없으면 빈값을 반환한다")
	void search_Cafes_No_Filtering_When_No_Data_Then_EmptyList() {
		List<CafeImpl> cafes = cafeRepository.findWithBasicDetails();
		assertThat(cafes).isEqualTo(Collections.emptyList());
	}
}