package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;
import com.example.demo.dto.UpdateAttendanceResponse;

@Service
public class StudyOnceServiceImpl implements StudyOnceService {
	@Override
	public void tryJoin(String memberIdThatExpectedToJoin, String studyId) {

	}

	@Override
	public void tryQuit(String memberIdThatExpectedToQuit, String studyId) {

	}

	@Override
	public void tryCancel(String memberIdThatExpectedToCancel, String studyId) {

	}

	@Override
	public PagedResponse<StudyOnceSearchResponse> searchStudy(StudyOnceSearchRequest studyOnceSearchRequest) {
		return null;
	}

	@Override
	public List<UpdateAttendanceResponse> updateAttendance(String leaderId, String memberId, boolean attendance) {
		return null;
	}

	@Override
	public StudyOnceSearchResponse createStudy(String leaderId, StudyOnceCreateRequest studyOnceCreateRequest) {
		return null;
	}
}
