package com.example.demo.factory;

import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;

public class TestCafeStudyTagFactory {

	public static CafeStudyTagEntity createCafeStudyTag(CafeStudyTagType cafeStudyTagType) {
		return CafeStudyTagEntity.builder()
			.type(cafeStudyTagType)
			.build();
	}
}
