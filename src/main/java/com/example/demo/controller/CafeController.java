package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CafeResponse;
import com.example.demo.dto.CafeSearchRequest;
import com.example.demo.dto.CafeSearchResponse;
import com.example.demo.dto.PagedResponse;
import com.example.demo.service.CafeQueryService;
import com.example.demo.service.CafeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CafeController {

	private final CafeQueryService cafeQueryService;
	private final CafeService cafeService;

	@GetMapping("/cafe/list")
	public ResponseEntity<PagedResponse<CafeSearchResponse>> cafeList(
		@ModelAttribute CafeSearchRequest cafeSearchRequest) {
		PagedResponse<CafeSearchResponse> response = cafeQueryService.searchWithPagingByDynamicFilter(
			cafeSearchRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/cafe/{cafeId}")
	public ResponseEntity<CafeResponse> cafe(@PathVariable Long cafeId) {
		CafeResponse response = cafeQueryService.findCafeById(cafeId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
