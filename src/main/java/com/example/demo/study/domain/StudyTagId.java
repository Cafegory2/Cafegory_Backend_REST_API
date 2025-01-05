package com.example.demo.study.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyTagId {

    private final Long id;

    public boolean isSameId(StudyTagId id) {
        return this.id.equals(id.getId());
    }
}
