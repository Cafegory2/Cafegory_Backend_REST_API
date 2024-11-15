package com.example.demo.factory;

import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.cafe.infrastructure.CafeTagEntity;

public class TestCafeTagFactory {

	public static CafeTagEntity createCafeTag(CafeTagType cafeTagType) {
		return CafeTagEntity.builder()
			.type(cafeTagType)
			.build();
	}
}
