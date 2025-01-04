package com.example.demo.cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.implement.BusinessHourReader;
import com.example.demo.cafe.implement.CafeReader;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.presentation.CafeSearchListRequest;
import com.example.demo.cafe.presentation.CafeSearchListResponse;
import com.example.demo.trash.dto.SliceResponse;
import com.example.demo.util.PagingUtil;
import com.example.demo.util.TimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeQueryService {

	private final CafeReader cafeReader;
	private final BusinessHourReader businessHourReader;

	private final TimeUtil timeUtil;

	public Cafe getCafe(Long cafeId) {
		return cafeReader.getWithTags(cafeId);
	}

	public SliceResponse<CafeSearchListResponse> searchCafesByDynamicFilter(CafeSearchListRequest request) {
		Pageable pageable = PagingUtil.of(request.getPage(), request.getSizePerPage());

		applyDefaultTimes(request);
		SliceResponse<CafeEntity> cafeEntities = cafeReader.readCafes(request);

		List<CafeSearchListResponse> cafeResponses = cafeEntities.getContent().stream()
			.map(cafe -> {
				BusinessHour businessHour = businessHourReader.readBy(cafe.getId(), timeUtil.now().getDayOfWeek());
				return CafeSearchListResponse.from(cafe, businessHour);
			})
			.collect(Collectors.toList());

		return SliceResponse.of(PagingUtil.toSlice(cafeResponses, pageable));
	}

	private void applyDefaultTimes(CafeSearchListRequest request) {
		request.applyDefaultOpeningTime(timeUtil.minLocalDateTime(timeUtil.now()));
		request.applyDefaultClosingTime(timeUtil.maxLocalDateTime(timeUtil.now()));
	}

	// private List<CafeStudyEntity> filterAndSortByIdDesc(List<CafeStudyEntity> cafeStudies,
	// 	Predicate<CafeStudyEntity> predicate) {
	// 	return cafeStudies.stream()
	// 		.filter(predicate)
	// 		.sorted(Comparator.comparing(CafeStudyEntity::getId).reversed())
	// 		.collect(Collectors.toList());
	// }
}
