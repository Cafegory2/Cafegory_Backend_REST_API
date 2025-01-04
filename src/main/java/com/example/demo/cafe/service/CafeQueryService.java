package com.example.demo.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.domain.CafeId;
import com.example.demo.cafe.implement.BusinessHourReader;
import com.example.demo.cafe.implement.CafeReader;
import com.example.demo.cafe.presentation.CafeSearchListRequest;
import com.example.demo.cafe.presentation.CafeSearchListResponse;
import com.example.demo.util.TimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeQueryService {

	private final CafeReader cafeReader;
	private final BusinessHourReader businessHourReader;

	private final TimeUtil timeUtil;

	public Cafe getCafe(CafeId cafeId) {
		return cafeReader.getWithTags(cafeId);
	}

	public SliceResponse<CafeSearchListResponse> searchCafesByDynamicFilter(CafeSearchListRequest request) {
		applyDefaultTimes(request);
		SliceResponse<Cafe> cafes = cafeReader.readCafes(request);

		return cafes.map(
			cafe -> {
				BusinessHour businessHour = businessHourReader.readBy(cafe.getId(), timeUtil.now().getDayOfWeek());

				return CafeSearchListResponse.from(cafe, businessHour);
			}
		);

		// List<CafeSearchListResponse> cafeResponses = cafes.getContent().stream()
		// 	.map(cafe -> {
		// 		BusinessHour businessHour = businessHourReader.readBy(cafe.getId(), timeUtil.now().getDayOfWeek());
		//
		// 		return CafeSearchListResponse.from(cafe, businessHour);
		// 	})
		// 	.collect(Collectors.toList());
		//
		// return SliceResponse.of(PagingUtil.toSlice(cafeResponses, pageable));
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
