package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CafeSearchRequest;
import com.example.demo.dto.CafeSearchResponse;
import com.example.demo.dto.PagedResponse;
import com.example.demo.service.CafeQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CafeController {

	private final CafeQueryService cafeQueryService;

	@GetMapping("/cafe/list")
	public ResponseEntity<PagedResponse<CafeSearchResponse>> cafeList(
		@ModelAttribute CafeSearchRequest cafeSearchRequest) {

		System.out.println("cafeSearchRequest = " + cafeSearchRequest);
		// PagedResponse<CafeSearchResponse> response = cafeQueryService.searchWithPagingByDynamicFilter(
		// 	new ControllerCafeMapper().convertToServiceCafeSearchRequest(cafeSearchRequest)
		// );
		PagedResponse<CafeSearchResponse> response = cafeQueryService.searchWithPagingByDynamicFilter(
			cafeSearchRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
