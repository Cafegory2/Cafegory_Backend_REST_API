package com.example.demo.service;

import com.example.demo.service.dto.LogicResult;
import com.example.demo.service.dto.PagedLogicResult;
import com.example.demo.service.dto.StudySearchBase;
import com.example.demo.service.dto.StudySearchResult;

public interface StudyService {
	LogicResult<Boolean> tryJoin(String memberIdThatExpectedToJoin, String studyId);

	LogicResult<Boolean> tryQuit(String memberIdThatExpectedToQuit, String studyId);

	LogicResult<Boolean> tryCancel(String memberIdThatExpectedToCancel, String studyId);

	PagedLogicResult<StudySearchResult> searchStudy(StudySearchBase studySearchBase);
}
