package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BusinessHour;
import com.example.demo.domain.BusinessHourOpenChecker;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.OpenChecker;
import com.example.demo.dto.BusinessHourResponse;
import com.example.demo.dto.CafeSearchCondition;
import com.example.demo.dto.CafeSearchRequest;
import com.example.demo.dto.CafeSearchResponse;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.SnsResponse;
import com.example.demo.repository.cafe.CafeQueryRepository;
import com.example.demo.util.PageRequestCustom;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeQueryServiceImpl implements CafeQueryService {

	private final CafeQueryRepository cafeQueryRepository;

	@Override
	public PagedResponse<CafeSearchResponse> searchWithPagingByDynamicFilter(CafeSearchRequest request) {

		Pageable pageable = PageRequestCustom.of(request.getPage(), request.getSizePerPage());

		CafeSearchCondition newSearchCondition = new CafeSearchCondition.Builder(request.isCanStudy(),
			request.getArea())
			.maxTime(request.getMaxTime())
			.minMenuPrice(request.getMinBeveragePrice())
			.startTime(request.getStartTime())
			.endTime(request.getEndTime())
			.build();

		Page<CafeImpl> pagedCafes = cafeQueryRepository.findWithDynamicFilter(newSearchCondition,
			pageable);

		OpenChecker<BusinessHour> openChecker = new BusinessHourOpenChecker();

		List<CafeSearchResponse> cafeSearchResponses = pagedCafes.getContent().stream()
			.map(cafe ->
				new CafeSearchResponse(
					cafe.getId(),
					cafe.getName(),
					cafe.showFullAddress(),
					cafe.getBusinessHours().stream()
						.map(hour -> new BusinessHourResponse(hour.getDay(), hour.getStartTime().toString(),
							hour.getEndTime().toString()))
						.collect(Collectors.toList()),
					cafe.isOpen(openChecker),
					cafe.getSnsDetails().stream()
						.map(s -> new SnsResponse(s.getName(), s.getUrl()))
						.collect(Collectors.toList()),
					cafe.getPhone(),
					cafe.getMinBeveragePrice(),
					cafe.getMaxAllowableStay().getValue(),
					cafe.getAvgReviewRate()
				)
			)
			.collect(Collectors.toList());
		return PagedResponse.createWithFirstPageAsOne(
			pagedCafes.getNumber(),
			pagedCafes.getTotalPages(),
			pagedCafes.getNumberOfElements(),
			cafeSearchResponses
		);
	}

}
