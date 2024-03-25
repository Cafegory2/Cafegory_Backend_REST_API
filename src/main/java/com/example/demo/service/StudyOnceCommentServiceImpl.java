package com.example.demo.service;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyOnceComment;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.dto.StudyOnceCommentRequest;
import com.example.demo.dto.StudyOnceCommentUpdateRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.StudyOnceCommentRepository;
import com.example.demo.repository.StudyOnceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyOnceCommentServiceImpl implements StudyOnceCommentService {

	private final StudyOnceCommentRepository studyOnceCommentRepository;
	private final MemberRepository memberRepository;
	private final StudyOnceRepository studyOnceRepository;

	@Override
	public Long saveQuestion(Long memberId, Long studyOnceId, StudyOnceCommentRequest request) {
		StudyOnceComment question = StudyOnceComment.builder()
			.content(request.getContent())
			.member(findMemberById(memberId))
			.studyOnce(findStudyOnceById(studyOnceId))
			.build();
		StudyOnceComment savedQuestion = studyOnceCommentRepository.save(question);
		return savedQuestion.getId();
	}

	@Override
	public void updateComment(Long memberId, Long studyOnceCommentId, StudyOnceCommentUpdateRequest request) {
		StudyOnceComment question = findStudyOnceQuestionById(studyOnceCommentId);
		if (!isPersonWhoAskedComment(memberId, studyOnceCommentId)) {
			throw new CafegoryException(STUDY_ONCE_COMMENT_PERMISSION_DENIED);
		}
		question.changeContent(request.getContent());
	}

	@Override
	public boolean isPersonWhoAskedComment(Long memberId, Long studyOnceCommentId) {
		StudyOnceComment question = findStudyOnceQuestionById(studyOnceCommentId);
		return question.isPersonAsked(findMemberById(memberId));
	}

	@Override
	public Long saveReply(Long memberId, Long studyOnceId, Long parentStudyOnceCommentId,
		StudyOnceCommentRequest request) {
		StudyOnceComment reply = StudyOnceComment.builder()
			.content(request.getContent())
			.member(findMemberById(memberId))
			.studyOnce(findStudyOnceById(studyOnceId))
			.parent(findStudyOnceQuestionById(parentStudyOnceCommentId))
			.build();
		StudyOnceComment savedReply = studyOnceCommentRepository.save(reply);
		return savedReply.getId();
	}

	@Override
	public void deleteQuestion(Long studyOnceQuestionId) {
		StudyOnceComment question = findStudyOnceQuestionById(studyOnceQuestionId);
		studyOnceCommentRepository.delete(question);
	}

	private StudyOnceComment findStudyOnceQuestionById(Long studyOnceQuestionId) {
		return studyOnceCommentRepository.findById(studyOnceQuestionId)
			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_COMMENT_NOT_FOUND));
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
