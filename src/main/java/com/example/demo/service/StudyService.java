package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudySearchRequest;
import com.example.demo.dto.StudySearchResponse;
import com.example.demo.dto.UpdateAttendanceResponse;

public interface StudyService {
	void tryJoin(String memberIdThatExpectedToJoin, String studyId);

	void tryQuit(String memberIdThatExpectedToQuit, String studyId);

	void tryCancel(String memberIdThatExpectedToCancel, String studyId);

	PagedResponse<StudySearchResponse> searchStudy(StudySearchRequest studySearchBase);

	List<UpdateAttendanceResponse> updateAttendance(String leaderId, String memberId, boolean attendance);
}
