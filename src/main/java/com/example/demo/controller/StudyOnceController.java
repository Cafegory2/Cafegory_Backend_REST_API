package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;

@RestController
@RequestMapping("/study/once")
public class StudyOnceController {
	@GetMapping("/study/once/{studyId:[0-9]+}")
	public ResponseEntity<StudyOnceSearchResponse> search(Long studyId) {
		return ResponseEntity.ok(new StudyOnceSearchResponse());
	}

	@GetMapping("/study/once/list}")
	public ResponseEntity<PagedResponse<StudyOnceSearchResponse>> searchList(
		StudyOnceSearchRequest studyOnceSearchRequest) {
		PagedResponse<StudyOnceSearchResponse> pagedResponse = new PagedResponse<>(1, 1, 1,
			List.of(new StudyOnceSearchResponse()));
		return ResponseEntity.ok(pagedResponse);
	}

	@PostMapping("/study/once")
	public ResponseEntity<StudyOnceSearchResponse> create(StudyOnceCreateRequest studyOnceCreateRequest) {
		return ResponseEntity.ok(new StudyOnceSearchResponse());
	}
}
