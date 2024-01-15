package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Member;
import com.example.demo.service.dto.LogicResult;
import com.example.demo.service.dto.PagedLogicResult;
import com.example.demo.service.dto.StudySearchBase;
import com.example.demo.service.dto.StudySearchResult;

public interface StudyService {
	LogicResult<Boolean> tryJoin(String memberIdThatExpectedToJoin, String studyId);

	LogicResult<Boolean> tryQuit(String memberIdThatExpectedToQuit, String studyId);

	LogicResult<Boolean> tryCancel(String memberIdThatExpectedToCancel, String studyId);

	PagedLogicResult<StudySearchResult> searchStudy(StudySearchBase studySearchBase);

	List<LogicResult<Boolean>> updateAttendance(Member leader, Member member, boolean attendance);
}
