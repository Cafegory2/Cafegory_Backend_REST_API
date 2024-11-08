package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Schedule {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    //TODO 문서화, 주석 필수
    public boolean contains (Schedule schedule) {

        this.startDateTime.isBefore(schedule.endDateTime)
    }
}
