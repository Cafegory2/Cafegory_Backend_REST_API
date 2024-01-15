package com.example.demo.domain;

import java.util.List;

import com.example.demo.service.dto.LogicResult;
import com.example.demo.service.dto.PagedLogicResult;
import com.example.demo.service.dto.StudySearchBase;

public interface Study {

	PagedLogicResult<Study> searchStudies(StudySearchBase studySearchBase);

	LogicResult<Boolean> tryJoin(Member memberThatExpectedToJoin);

	/**
	 * 스터디 참여를 취소하는 로직이 구현된 메서드
	 * @param memberThatExpectedToQuit 스터디 참여를 취소하고자 하는 멤버
	 * @return 스터디 참여 취소 요청 결과
	 */
	LogicResult<Boolean> tryQuit(Member memberThatExpectedToQuit);

	/**
	 * 스터디 자체를 취소하는 로직이 구현된 메서드
	 * @param memberThatExpectedToCancel 스터디를 취소하고자 하는 멤버
	 * @return 스터디 취소 요청 결괴
	 */
	LogicResult<Boolean> tryCancel(Member memberThatExpectedToCancel);

	List<LogicResult<Boolean>> updateAttendance(Member leader, Member member, boolean attendance);
}
