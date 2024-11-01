package com.example.demo.factory;

import com.example.demo.implement.study.CafeStudyCafeStudyTagEntity;
import com.example.demo.implement.study.CafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;

public class TestCafeStudyCafeStudyTagFactory {

	public static CafeStudyCafeStudyTagEntity createCafeStudyCafeStudyTag(CafeStudyEntity cafeStudy,
		CafeStudyTagEntity cafeStudyTag) {
		return CafeStudyCafeStudyTagEntity.builder()
			.cafeStudy(cafeStudy)
			.cafeStudyTag(cafeStudyTag)
			.build();
	}
}
