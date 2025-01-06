package com.example.demo.domain.study.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyStudyTagId {

    private final Long id;

    public boolean isSameId(StudyStudyTagId id) {
        return this.id.equals(id.getId());
    }
}
