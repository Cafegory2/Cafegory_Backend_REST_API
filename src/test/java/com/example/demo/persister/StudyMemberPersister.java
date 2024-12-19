package com.example.demo.persister;

import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.Attendance;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyMemberEntity;
import com.example.demo.study.infrastructure.StudyMemberRepository;

public class StudyMemberPersister {

    private CafeStudyEntity study;
    private MemberEntity member;
    private StudyRole studyRole = StudyRole.MEMBER;
    private Attendance attendance = Attendance.YES;

    private StudyMemberPersister() {}

    private StudyMemberPersister(StudyMemberPersister copy) {
        this.study = copy.study;
        this.member = copy.member;
        this.studyRole = copy.studyRole;
        this.attendance = copy.attendance;
    }

    public StudyMemberPersister but() {
        return new StudyMemberPersister(this);
    }

    public static StudyMemberPersister aStudyMember() {
        return new StudyMemberPersister();
    }

    public StudyMemberPersister withStudy(CafeStudyEntity study) {
        this.study = study;
        return this;
    }

    public StudyMemberPersister withMember(MemberEntity member) {
        this.member = member;
        return this;
    }

    public StudyMemberPersister withStudyRole(StudyRole studyRole) {
        this.studyRole = studyRole;
        return this;
    }

    public StudyMemberPersister asParticipant() {
        this.studyRole = StudyRole.MEMBER;
        return this;
    }

    public StudyMemberPersister asCoordinator() {
        this.studyRole = StudyRole.COORDINATOR;
        return this;
    }

    public StudyMemberPersister withAttendance(Attendance attendance) {
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

    public static class StudyMemberRepoHolder {
        private static StudyMemberRepository studyMemberRepository;

        public static void init(StudyMemberRepository studyMemberRepo) {
            studyMemberRepository = studyMemberRepo;
        }
    }

    public CafeStudyMemberEntity save() {
        return StudyMemberRepoHolder.studyMemberRepository.save(build());
    }
}
