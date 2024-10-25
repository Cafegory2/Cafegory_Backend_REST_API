package com.example.demo.cafe.domain;

import com.example.demo.domain.DateAudit;
import com.example.demo.implement.study.CafeTagType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Review {

    private Long id;
    private List<CafeTagType> tags;
    private Cafe cafe;
    private DateAudit dateAudit;
}
