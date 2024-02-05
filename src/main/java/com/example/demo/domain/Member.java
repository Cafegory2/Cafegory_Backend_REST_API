package com.example.demo.domain;

import java.util.List;

public interface Member {
	Long getId();

	List<StudyMember> getStudyMembers();

	void addStudyMember(StudyMember studyMember);
}
