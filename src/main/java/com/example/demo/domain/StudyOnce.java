package com.example.demo.domain;

public interface StudyOnce {

	void tryJoin(Member memberThatExpectedToJoin);

	/**
	 * 스터디 참여를 취소하는 로직이 구현된 메서드
	 * @param memberThatExpectedToQuit 스터디 참여를 취소하고자 하는 멤버
	 */
	void tryQuit(Member memberThatExpectedToQuit);

	/**
	 * 스터디 자체를 취소하는 로직이 구현된 메서드
	 * @param memberThatExpectedToCancel 스터디를 취소하고자 하는 멤버
	 */
	void tryCancel(Member memberThatExpectedToCancel);

	void updateAttendance(Member leader, Member member, boolean attendance);
}
