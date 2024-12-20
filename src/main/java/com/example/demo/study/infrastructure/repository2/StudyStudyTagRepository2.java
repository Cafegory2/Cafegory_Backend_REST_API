package com.example.demo.study.infrastructure.repository2;

import java.util.List;

public interface StudyStudyTagRepository2 {

    List<Long> saveAll(Long studyId, List<Long> studyTagIds);
}
