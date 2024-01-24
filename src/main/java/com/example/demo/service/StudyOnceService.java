package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;
import com.example.demo.dto.UpdateAttendanceResponse;

public interface StudyOnceService {
	void tryJoin(String memberIdThatExpectedToJoin, String studyId);

	void tryQuit(String memberIdThatExpectedToQuit, String studyId);

	void tryCancel(String memberIdThatExpectedToCancel, String studyId);

	PagedResponse<StudyOnceSearchResponse> searchStudy(StudyOnceSearchRequest studyOnceSearchRequest);

	List<UpdateAttendanceResponse> updateAttendance(String leaderId, String memberId, boolean attendance);

	StudyOnceSearchResponse createStudy(String leaderId, StudyOnceCreateRequest studyOnceCreateRequest);
}
