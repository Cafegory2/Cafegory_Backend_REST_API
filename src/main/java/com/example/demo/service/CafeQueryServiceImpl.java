package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.CafeImpl;
import com.example.demo.dto.PagedResponse;
import com.example.demo.repository.cafe.CafeQueryRepository;
import com.example.demo.service.dto.BusinessHourDto;
import com.example.demo.service.dto.CafeSearchResponse;
import com.example.demo.service.dto.ServiceCafeSearchRequest;
import com.example.demo.service.dto.SnsDto;
import com.example.demo.util.PageRequestCustom;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeQueryServiceImpl implements CafeQueryService {

	private final CafeQueryRepository cafeQueryRepository;

	@Override
	public PagedResponse<CafeSearchResponse> searchWithPagingByDynamicFilter(ServiceCafeSearchRequest request) {
		Pageable pageable = PageRequestCustom.of(request.getPage(), request.getSizePerPage());
		Page<CafeImpl> pagedCafes = cafeQueryRepository.findWithDynamicFilter(request.getSearchCondition(),
			pageable);
		System.out.println("pagedCafes = " + pagedCafes.getContent());
		List<CafeSearchResponse> cafeSearchResponses = pagedCafes.getContent().stream()
			.map(cafe ->
				new CafeSearchResponse(
					cafe.getId(),
					cafe.getName(),
					cafe.getAddress().showFullAddress(),
					cafe.getBusinessHours().stream()
						.map(hour -> new BusinessHourDto(hour.getDay(), hour.getStartTime().toString(),
							hour.getEndTime().toString()))
						.collect(Collectors.toList()),
					true,
					cafe.getSnsDetails().stream()
						.map(s -> new SnsDto(s.getName(), s.getUrl()))
						.collect(Collectors.toList()),
					cafe.getPhone(),
					1000,
					cafe.getMaxAllowableStay().getValue(),
					cafe.getAvgReviewRate()
				)
			)
			.collect(Collectors.toList());
		return new PagedResponse<>(
			pagedCafes.getNumber(),
			pagedCafes.getTotalPages(),
			pagedCafes.getNumberOfElements(),
			cafeSearchResponses);
	}

}
