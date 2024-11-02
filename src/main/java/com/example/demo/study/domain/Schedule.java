package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Schedule {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
