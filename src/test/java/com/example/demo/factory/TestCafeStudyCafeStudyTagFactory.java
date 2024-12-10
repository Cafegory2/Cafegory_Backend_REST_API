package com.example.demo.factory;

import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;

public class TestCafeStudyCafeStudyTagFactory {

	public static CafeStudyCafeStudyTagEntity createCafeStudyCafeStudyTag(CafeStudyEntity cafeStudy,
		CafeStudyTagEntity cafeStudyTag) {
		return CafeStudyCafeStudyTagEntity.builder()
			.cafeStudy(cafeStudy)
			.cafeStudyTag(cafeStudyTag)
			.build();
	}
}
