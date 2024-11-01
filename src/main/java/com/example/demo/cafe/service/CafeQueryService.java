package com.example.demo.cafe.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.implement.BusinessHourOpenChecker;
import com.example.demo.cafe.implement.CafeReader;
import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.dto.cafe.CafeDetailResponse;
import com.example.demo.implement.cafe.BusinessHourReader;
import com.example.demo.study.implement.CafeStudyReader;
import com.example.demo.study.infrastructure.CafeStudyEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeQueryService {

	private final CafeReader cafeReader;
	private final CafeStudyReader cafeStudyReader;
	private final BusinessHourReader businessHourReader;
	private final BusinessHourOpenChecker businessHourOpenChecker;

	public CafeDetailResponse getCafeDetail(Long cafeId, LocalDateTime now) {
		CafeEntity cafeEntity = cafeReader.getWithTags(cafeId);
		BusinessHourEntity businessHourEntity = businessHourReader.readBy(cafeEntity.toCafe(),
			now.getDayOfWeek());

		List<CafeStudyEntity> cafeStudies = cafeStudyReader.readAllWithCoordinatorBy(cafeId);
		List<CafeStudyEntity> openStudies = filterAndSortByIdDesc(cafeStudies, CafeStudyEntity::isRecruitmentOpen);
		List<CafeStudyEntity> closeStudies = filterAndSortByIdDesc(cafeStudies, (study) -> !study.isRecruitmentOpen());

		return CafeDetailResponse.of(cafeEntity, businessHourEntity,
			businessHourOpenChecker.checkByNowTime(
				businessHourEntity.getDayOfWeek(), businessHourEntity.getOpeningTime(),
				businessHourEntity.getClosingTime(), now),
			openStudies, closeStudies
		);
	}

	private List<CafeStudyEntity> filterAndSortByIdDesc(List<CafeStudyEntity> cafeStudies,
		Predicate<CafeStudyEntity> predicate) {
		return cafeStudies.stream()
			.filter(predicate)
			.sorted(Comparator.comparing(CafeStudyEntity::getId).reversed())
			.collect(Collectors.toList());
	}
}
