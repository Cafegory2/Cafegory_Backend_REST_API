package com.example.demo.dto.review;

import com.example.demo.dto.PagedRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewSearchListRequest extends PagedRequest {
	private final Long cafeId;
}
