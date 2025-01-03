package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class StudyContent {

    private String name;
    private Schedule schedule;
    private MemberComms memberComms;
    private int maxParticipantCount;
    private String introduction;
    private List<CafeStudyTagType> tags;

    public DayOfWeek getStartDate() {
        return schedule.getStartDateTime().getDayOfWeek();
    }

    public LocalDateTime getStartDateTime() {
        return schedule.getStartDateTime();
    }

    public LocalDateTime getEndDateTime() {
        return schedule.getEndDateTime();
    }
}
