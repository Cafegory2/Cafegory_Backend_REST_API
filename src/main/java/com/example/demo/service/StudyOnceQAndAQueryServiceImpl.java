package com.example.demo.service;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.StudyOnceQuestion;
import com.example.demo.dto.StudyOnceQuestionResponse;
import com.example.demo.dto.WriterResponse;
import com.example.demo.exception.CafegoryException;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.mapper.StudyOnceMapper;
import com.example.demo.repository.StudyOnceQuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyOnceQAndAQueryServiceImpl implements StudyOnceQAndAQueryService {

	private final StudyOnceQuestionRepository studyOnceQuestionRepository;
	private final MemberMapper memberMapper;
	private final StudyOnceMapper studyOnceMapper;

	@Override
	public StudyOnceQuestionResponse searchQuestion(Long studyOnceQuestionId) {
		StudyOnceQuestion question = findStudyOnceQuestionById(studyOnceQuestionId);
		WriterResponse writerResponse = memberMapper.toWriterResponse(question.getMember());
		return studyOnceMapper.toStudyOnceQuestionResponse(question, writerResponse);
	}

	private StudyOnceQuestion findStudyOnceQuestionById(Long studyOnceQuestionId) {
		return studyOnceQuestionRepository.findById(studyOnceQuestionId)
			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_QUESTION_NOT_FOUND));
	}
}
