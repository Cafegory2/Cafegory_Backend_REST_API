package com.example.demo.cafe.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeTags {
	private List<CafeTagType> cafeTagTypes = new ArrayList<>();
}
