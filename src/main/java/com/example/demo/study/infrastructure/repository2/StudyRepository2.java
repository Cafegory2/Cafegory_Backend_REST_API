package com.example.demo.study.infrastructure.repository2;

import com.example.demo.study.domain.Study;

public interface StudyRepository2 {

	public Study save(Study study, Long memberId);

}
