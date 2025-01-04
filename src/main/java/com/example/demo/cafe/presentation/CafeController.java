package com.example.demo.cafe.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.domain.CafeId;
import com.example.demo.cafe.service.BusinessHourService;
import com.example.demo.cafe.service.CafeQueryService;
import com.example.demo.util.TimeUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafes")
public class CafeController {

	private final CafeQueryService cafeQueryService;
	private final BusinessHourService businessHourService;
	private final TimeUtil timeUtil;

	@GetMapping("/{cafeId}")
	public ResponseEntity<CafeDetailResponse> getCafeDetail(@PathVariable Long cafeId) {
		Cafe cafe = cafeQueryService.getCafe(new CafeId(cafeId));
		BusinessHour businessHour = businessHourService.findBusinessHour(cafe.getId(), timeUtil.now());
		boolean isOpen = businessHourService.isOpen(businessHour, timeUtil.now());

		CafeDetailResponse response = CafeDetailResponse.of(cafe, businessHour, isOpen);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<SliceResponse<CafeSearchListResponse>> getCafes(
		@Validated @ModelAttribute CafeSearchListRequest request
	) {
		SliceResponse<CafeSearchListResponse> response = cafeQueryService.searchCafesByDynamicFilter(request);
		return ResponseEntity.ok(response);
	}
}
