package com.example.demo.service;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.domain.StudyOnceQuestion;
import com.example.demo.dto.StudyOnceQuestionRequest;
import com.example.demo.dto.StudyOnceQuestionUpdateRequest;
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
	public Long saveQuestion(Long memberId, Long studyOnceId, StudyOnceQuestionRequest request) {
		StudyOnceQuestion question = StudyOnceQuestion.builder()
			.content(request.getContent())
			.member(findMemberById(memberId))
			.studyOnce(findStudyOnceById(studyOnceId))
			.build();
		StudyOnceQuestion savedQuestion = studyOnceQuestionRepository.save(question);
		return savedQuestion.getId();
	}

	@Override
	public void updateQuestion(Long memberId, Long studyOnceQuestionId, StudyOnceQuestionUpdateRequest request) {
		StudyOnceQuestion question = findStudyOnceQuestionById(studyOnceQuestionId);
		if (!isPersonWhoAskedQuestion(memberId, studyOnceQuestionId)) {
			throw new CafegoryException(STUDY_ONCE_QUESTION_PERMISSION_DENIED);
		}
		question.changeContent(request.getContent());
	}

	@Override
	public boolean isPersonWhoAskedQuestion(Long memberId, Long studyOnceQuestionId) {
		StudyOnceQuestion question = findStudyOnceQuestionById(studyOnceQuestionId);
		return question.isPersonAsked(findMemberById(memberId));
	}

	@Override
	public Long saveReply(Long memberId, Long studyOnceId, Long parentStudyOnceQuestionId,
		StudyOnceQuestionRequest request) {
		StudyOnceQuestion reply = StudyOnceQuestion.builder()
			.content(request.getContent())
			.member(findMemberById(memberId))
			.studyOnce(findStudyOnceById(studyOnceId))
			.parent(findStudyOnceQuestionById(parentStudyOnceQuestionId))
			.build();
		StudyOnceQuestion savedReply = studyOnceQuestionRepository.save(reply);
		return savedReply.getId();
	}

	@Override
	public void deleteQuestion(Long studyOnceQuestionId) {
		StudyOnceQuestion question = findStudyOnceQuestionById(studyOnceQuestionId);
		studyOnceQuestionRepository.delete(question);
	}

	private StudyOnceQuestion findStudyOnceQuestionById(Long studyOnceQuestionId) {
		return studyOnceQuestionRepository.findById(studyOnceQuestionId)
			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_QUESTION_NOT_FOUND));
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
