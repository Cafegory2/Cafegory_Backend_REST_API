package com.example.demo.domain;

import java.util.List;

public interface Member {
	List<StudyMember> getStudyMembers();

	void addStudyMember(StudyMember studyMember);
}
