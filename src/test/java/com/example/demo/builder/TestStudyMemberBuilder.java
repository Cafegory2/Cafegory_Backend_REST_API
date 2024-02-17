package com.example.demo.builder;

import com.example.demo.domain.Attendance;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyMember;
import com.example.demo.domain.StudyOnceImpl;

public class TestStudyMemberBuilder {

	// private StudyMemberId id;
	private MemberImpl member;
	private StudyOnceImpl study;
	private Attendance attendance = Attendance.YES;

	// public TestStudyMemberBuilder id(StudyMemberId id) {
	// 	this.id = id;
	// 	return this;
	// }

	public TestStudyMemberBuilder member(MemberImpl member) {
		this.member = member;
		return this;
	}

	public TestStudyMemberBuilder study(StudyOnceImpl study) {
		this.study = study;
		return this;
	}

	public TestStudyMemberBuilder attendanceYes() {
		this.attendance = Attendance.YES;
		return this;
	}

	public StudyMember build(MemberImpl member, StudyOnceImpl study) {
		return StudyMember.builder()
			.member(member)
			.study(study)
			.build();
	}
}
