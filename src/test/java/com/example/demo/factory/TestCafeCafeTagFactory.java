package com.example.demo.factory;

import com.example.demo.cafe.infrastructure.CafeCafeTagEntity;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeTagEntity;

public class TestCafeCafeTagFactory {

	public static CafeCafeTagEntity createCafeCafeTag(CafeEntity cafeEntity, CafeTagEntity cafeTag) {
		return CafeCafeTagEntity.builder()
			.cafe(cafeEntity)
			.cafeTag(cafeTag)
			.build();
	}
}
