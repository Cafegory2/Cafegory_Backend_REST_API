package com.example.demo.persister;

import com.example.demo.db.member.MemberEntity;
import com.example.demo.db.study.study.CafeStudyEntity;
import com.example.demo.db.study.studymember.CafeStudyMemberEntity;
import com.example.demo.db.study.studymember.StudyMemberJpaRepository;
import com.example.demo.domain.study.domain.Attendance;
import com.example.demo.domain.study.domain.StudyRole;

public class StudyMemberPersister {

	private CafeStudyEntity study;
	private MemberEntity member;
	private StudyRole studyRole = StudyRole.MEMBER;
	private Attendance attendance = Attendance.YES;

	private StudyMemberPersister() {
	}

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
		private static StudyMemberJpaRepository studyMemberJpaRepository;

		public static void init(StudyMemberJpaRepository studyMemberRepo) {
			studyMemberJpaRepository = studyMemberRepo;
		}
	}

	public CafeStudyMemberEntity persist() {
		return StudyMemberRepoHolder.studyMemberJpaRepository.save(build());
	}
}
