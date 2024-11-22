package com.example.demo.cafe.service;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.example.demo.cafe.domain.Cafe;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.implement.BusinessHourOpenChecker;
import com.example.demo.cafe.implement.BusinessHourReader;
import com.example.demo.cafe.implement.CafeReader;
import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.BusinessHourRepository;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.presentation.CafeDetailResponse;
import com.example.demo.exception.CafegoryException;
import com.example.demo.study.implement.StudyReader;
import com.example.demo.study.infrastructure.CafeStudyEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeQueryService {

	private final CafeReader cafeReader;
	private final StudyReader cafeStudyReader;
	private final BusinessHourReader businessHourReader;
	private final BusinessHourOpenChecker businessHourOpenChecker;

	private final BusinessHourRepository businessHourRepository;

	public CafeDetailResponse getCafeDetail(Long cafeId, LocalDateTime now) {
		CafeEntity cafeEntity = cafeReader.getWithTagsEntity(cafeId);
		BusinessHour businessHour = businessHourReader.readBy(cafeId, now.getDayOfWeek());

		List<CafeStudyEntity> cafeStudies = cafeStudyReader.readAllWithCoordinatorBy(cafeId);
		List<CafeStudyEntity> openStudies = filterAndSortByIdDesc(cafeStudies, CafeStudyEntity::isRecruitmentOpen);
		List<CafeStudyEntity> closeStudies = filterAndSortByIdDesc(cafeStudies, (study) -> !study.isRecruitmentOpen());

		BusinessHourEntity businessHourEntity = businessHourRepository.findById(businessHour.getId())
			.orElseThrow(() -> new CafegoryException(CAFE_BUSINESS_HOUR_NOT_FOUND));

		Cafe cafe = cafeReader.getWithTags(cafeId);

		return CafeDetailResponse.of(cafe, cafeEntity, businessHourEntity,
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
