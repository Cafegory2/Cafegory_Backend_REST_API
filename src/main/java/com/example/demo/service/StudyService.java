package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LogicResult;
import com.example.demo.dto.PagedLogicResult;
import com.example.demo.dto.StudySearchBase;
import com.example.demo.dto.StudySearchResult;

public interface StudyService {
	LogicResult<Boolean> tryJoin(String memberIdThatExpectedToJoin, String studyId);

	LogicResult<Boolean> tryQuit(String memberIdThatExpectedToQuit, String studyId);

	LogicResult<Boolean> tryCancel(String memberIdThatExpectedToCancel, String studyId);

	PagedLogicResult<StudySearchResult> searchStudy(StudySearchBase studySearchBase);

	List<LogicResult<Boolean>> updateAttendance(String leaderId, String memberId, boolean attendance);
}
