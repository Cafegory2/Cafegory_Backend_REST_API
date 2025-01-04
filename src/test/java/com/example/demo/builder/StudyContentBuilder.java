package com.example.demo.builder;

import com.example.demo.study.domain.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.builder.ScheduleBuilder.aSchedule;


public class StudyContentBuilder {

    private String name = "테스트 스터디 이름";
    private Schedule schedule = aSchedule().build();
    private MemberComms memberComms = MemberComms.WELCOME;
    private int maxParticipantCount = 6;
    private String introduction = "테스트 스터디 소개";
    private List<CafeStudyTagType> tags = new ArrayList<>();

    private StudyContentBuilder() {}

    private StudyContentBuilder(StudyContentBuilder copy) {
        this.name = copy.name;
        this.schedule = copy.schedule;
        this.memberComms = copy.memberComms;
        this.maxParticipantCount = copy.maxParticipantCount;
        this.introduction = copy.introduction;
        this.tags = new ArrayList<>(copy.tags);
    }

    public StudyContentBuilder but() {
        return new StudyContentBuilder(this);
    }

    public static StudyContentBuilder aStudyContent() {
        return new StudyContentBuilder();
    }

    public StudyContentBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StudyContentBuilder with(ScheduleBuilder scheduleBuilder) {
        this.schedule = scheduleBuilder.build();
        return this;
    }

    public StudyContentBuilder withMemberComms(MemberComms memberComms) {
        this.memberComms = memberComms;
        return this;
    }

    public StudyContentBuilder withMaxParticipantCount(int maxParticipantCount) {
        this.maxParticipantCount = maxParticipantCount;
        return this;
    }

    public StudyContentBuilder withIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public StudyContentBuilder withTags(List<CafeStudyTagType> tags) {
        this.tags = tags;
        return this;
    }

    public StudyContentBuilder addTag(CafeStudyTagType tag) {
        this.tags.add(tag);
        return this;
    }

    public StudyContent build() {
        return StudyContent.builder()
                .name(this.name)
                .schedule(this.schedule)
                .memberComms(this.memberComms)
                .maxParticipantCount(this.maxParticipantCount)
                .introduction(this.introduction)
                .tags(this.tags)
                .build();
    }
}