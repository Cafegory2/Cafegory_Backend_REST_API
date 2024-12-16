package com.example.demo.testbuilder;

import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.Attendance;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyMemberEntity;
import com.example.demo.study.infrastructure.StudyMemberRepository;

import static com.example.demo.testbuilder.MemberBuilder.*;
import static com.example.demo.testbuilder.StudyBuilder.*;

public class StudyMemberBuilder {

    private CafeStudyEntity study = aStudy().build();
    private MemberEntity member = aMember().build();
    private StudyRole studyRole = StudyRole.MEMBER;
    private Attendance attendance = Attendance.YES;

    private StudyMemberBuilder() {}

    private StudyMemberBuilder(StudyMemberBuilder copy) {
        this.study = copy.study;
        this.member = copy.member;
        this.studyRole = copy.studyRole;
        this.attendance = copy.attendance;
    }

    public StudyMemberBuilder but() {
        return new StudyMemberBuilder(this);
    }

    public static StudyMemberBuilder aStudyMember() {
        return new StudyMemberBuilder();
    }

    public StudyMemberBuilder with(CafeStudyEntity study) {
        this.study = study;
        return this;
    }

    public StudyMemberBuilder with(MemberEntity member) {
        this.member = member;
        return this;
    }

    public StudyMemberBuilder withStudyRole(StudyRole studyRole) {
        this.studyRole = studyRole;
        return this;
    }

    public StudyMemberBuilder withAttendance(Attendance attendance) {
        this.attendance = attendance;
        return this;
    }

    public CafeStudyMemberEntity build() {
        CafeStudyMemberEntity studyMemberEntity = CafeStudyMemberEntity.builder()
                .cafeStudy(this.study)
                .member(this.member)
                .studyRole(this.studyRole)
                .build();

        studyMemberEntity.setAttendance(this.attendance);
        return studyMemberEntity;
    }

    public static class StudyMemberSaver {
        private static StudyMemberRepository studyMemberRepository;

        public static void init(StudyMemberRepository studyMemberRepo) {
            studyMemberRepository = studyMemberRepo;
        }
    }

    public CafeStudyMemberEntity save() {
        return StudyMemberSaver.studyMemberRepository.save(build());
    }
}