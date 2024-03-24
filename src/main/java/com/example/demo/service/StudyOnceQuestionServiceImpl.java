package com.example.demo.service;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.domain.StudyOnceQuestion;
import com.example.demo.exception.CafegoryException;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.StudyOnceQuestionRepository;
import com.example.demo.repository.StudyOnceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyOnceQuestionServiceImpl implements StudyOnceQuestionService {

	private final StudyOnceQuestionRepository studyOnceQuestionRepository;
	private final MemberRepository memberRepository;
	private final StudyOnceRepository studyOnceRepository;

	@Override
	public Long saveQuestion(Long memberId, Long studyOnceId, final String content) {
		StudyOnceQuestion question = StudyOnceQuestion.builder()
			.content(content)
			.member(findMemberById(memberId))
			.studyOnce(findStudyOnceById(studyOnceId))
			.build();
		StudyOnceQuestion savedQuestion = studyOnceQuestionRepository.save(question);
		return savedQuestion.getId();
	}

	private MemberImpl findMemberById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
	}

	private StudyOnceImpl findStudyOnceById(long studyOnceId) {
		return studyOnceRepository.findById(studyOnceId)
			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
	}
}
