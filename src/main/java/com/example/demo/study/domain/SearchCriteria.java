package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SearchCriteria {

    private String keyword;
    private LocalDate date;
    private CafeStudyTagType cafeStudyTagType;
    private MemberComms memberComms;
}
