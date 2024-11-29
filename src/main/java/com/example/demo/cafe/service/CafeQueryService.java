package com.example.demo.cafe.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.implement.BusinessHourOpenChecker;
import com.example.demo.cafe.implement.BusinessHourReader;
import com.example.demo.cafe.implement.CafeReader;
import com.example.demo.cafe.presentation.CafeDetailResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeQueryService {

	private final CafeReader cafeReader;
	private final BusinessHourReader businessHourReader;
	private final BusinessHourOpenChecker businessHourOpenChecker;

	public CafeDetailResponse getCafeDetail(Long cafeId, LocalDateTime now) {
		BusinessHour businessHour = businessHourReader.readBy(cafeId, now.getDayOfWeek());

		Cafe cafe = cafeReader.getWithTags(cafeId);

		return CafeDetailResponse.of(cafe, businessHour,
			businessHourOpenChecker.checkByNowTime(businessHour.getDayOfWeek(), businessHour.getOpeningTme(),
				businessHour.getClosingTme(), now)
		);
	}

	// private List<CafeStudyEntity> filterAndSortByIdDesc(List<CafeStudyEntity> cafeStudies,
	// 	Predicate<CafeStudyEntity> predicate) {
	// 	return cafeStudies.stream()
	// 		.filter(predicate)
	// 		.sorted(Comparator.comparing(CafeStudyEntity::getId).reversed())
	// 		.collect(Collectors.toList());
	// }
}
