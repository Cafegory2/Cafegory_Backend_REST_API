package com.example.demo.builder;

import com.example.demo.study.domain.Schedule;

import java.time.LocalDateTime;

public class ScheduleBuilder {

    private LocalDateTime startDateTime = LocalDateTime.now();
    private LocalDateTime endDateTime = startDateTime.plusHours(2);

    private ScheduleBuilder() {}

    private ScheduleBuilder(ScheduleBuilder copy) {
        this.startDateTime = copy.startDateTime;
        this.endDateTime = copy.endDateTime;
    }

    public static ScheduleBuilder aSchedule() {
        return new ScheduleBuilder();
    }

    public ScheduleBuilder but() {
        return new ScheduleBuilder(this);
    }

    public ScheduleBuilder withStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public ScheduleBuilder withEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public Schedule build() {
        return Schedule.builder()
                .startDateTime(this.startDateTime)
                .endDateTime(this.endDateTime)
                .build();
    }
}