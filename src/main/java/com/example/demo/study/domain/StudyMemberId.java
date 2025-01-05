package com.example.demo.study.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyMemberId {

    private final Long id;

    public boolean isSameId(StudyMemberId id) {
        return this.id.equals(id.getId());
    }
}
