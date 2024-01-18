package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PagedLogicResult;
import com.example.demo.dto.StudySearchBase;
import com.example.demo.dto.StudySearchResult;
import com.example.demo.dto.UpdateAttendanceResponse;

public interface StudyService {
	void tryJoin(String memberIdThatExpectedToJoin, String studyId);

	void tryQuit(String memberIdThatExpectedToQuit, String studyId);

	void tryCancel(String memberIdThatExpectedToCancel, String studyId);

	PagedLogicResult<StudySearchResult> searchStudy(StudySearchBase studySearchBase);

	List<UpdateAttendanceResponse> updateAttendance(String leaderId, String memberId, boolean attendance);
}
