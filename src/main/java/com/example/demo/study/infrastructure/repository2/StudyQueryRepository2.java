package com.example.demo.study.infrastructure.repository2;

import com.example.demo.study.domain.Study;

public interface StudyQueryRepository2 {

	Study findById(Long studyId);
}
