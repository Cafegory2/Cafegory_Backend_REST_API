package com.example.demo.cafe.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cafe.service.CafeQueryService;
import com.example.demo.util.TimeUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafes")
public class CafeController {

	private final CafeQueryService cafeQueryService;
	private final TimeUtil timeUtil;

	@GetMapping("/{cafeId}")
	public ResponseEntity<CafeDetailResponse> getCafeDetail(@PathVariable Long cafeId) {
		CafeDetailResponse response = cafeQueryService.getCafeDetail(cafeId, timeUtil.now());
		return ResponseEntity.ok(response);
	}
}
