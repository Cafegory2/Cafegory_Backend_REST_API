package com.example.demo.packageex.studyqna.service;

import com.example.demo.packageex.studyqna.domain.CafeStudyComment;
import com.example.demo.packageex.studyqna.implement.CafeStudyCommentReader;
import com.example.demo.packageex.studyqna.implement.CafeStudyQnaProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CafeStudyQnaService {

    private final CafeStudyQnaProcessor qnaProcessor;
    private final CafeStudyCommentReader commentReader;

    @Transactional
    public CafeStudyComment leaveComment(CafeStudyComment comment, Long memberId) {
        Long commentId = qnaProcessor.leaveComment(comment, memberId);
        return commentReader.read(commentId);
    }

// 	@Override
// 	public void updateQuestion(Long memberId, Long studyOnceCommentId, StudyOnceCommentUpdateRequest request) {
// 		StudyOnceComment question = findStudyOnceCommentById(studyOnceCommentId);
// 		if (question.hasReply()) {
// 			throw new CafegoryException(STUDY_ONCE_PARENT_COMMENT_MODIFICATION_BLOCKED);
// 		}
// 		validatePersonAsked(memberId, studyOnceCommentId);
// 		question.changeContent(request.getContent());
// 	}
//
// 	@Override
// 	public boolean isPersonWhoAskedComment(Long memberId, Long studyOnceCommentId) {
// 		StudyOnceComment question = findStudyOnceCommentById(studyOnceCommentId);
// 		return question.isPersonAsked(findMemberById(memberId));
// 	}
//
// 	@Override
// 	public Long saveReply(Long memberId, Long studyOnceId, Long parentStudyOnceCommentId,
// 		StudyOnceCommentRequest request) {
// 		CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
// 		Member member = findMemberById(memberId);
// 		if (!cafeStudy.isLeader(member)) {
// 			throw new CafegoryException(STUDY_ONCE_REPLY_PERMISSION_DENIED);
// 		}
// 		StudyOnceComment parentComment = findStudyOnceCommentById(parentStudyOnceCommentId);
// 		if (parentComment.hasParentComment()) {
// 			throw new CafegoryException(STUDY_ONCE_SINGLE_REPLY_PER_QUESTION);
// 		}
// 		if (!parentComment.hasParentComment() && parentComment.hasReply()) {
// 			throw new CafegoryException(STUDY_ONCE_SINGLE_REPLY_PER_QUESTION);
// 		}
// 		StudyOnceComment reply = StudyOnceComment.builder()
// 			.content(request.getContent())
// 			.member(member)
// 			.cafeStudy(cafeStudy)
// 			.parent(parentComment)
// 			.build();
// 		StudyOnceComment savedReply = studyOnceCommentRepository.save(reply);
// 		return savedReply.getId();
// 	}
//
// 	@Override
// 	public void deleteReply(Long studyOnceCommentId) {
// 		// 작성한 본인인지 검증하는 로직은 호출하는 쪽에서 한다. 관리자가 호출할 수 있기 때문이다.
// 		StudyOnceComment comment = findStudyOnceCommentById(studyOnceCommentId);
// 		studyOnceCommentRepository.delete(comment);
// 	}
//
// 	@Override
// 	public void updateReply(Long memberId, Long studyOnceCommentId, StudyOnceCommentUpdateRequest request) {
// 		StudyOnceComment question = findStudyOnceCommentById(studyOnceCommentId);
// 		validatePersonAsked(memberId, studyOnceCommentId);
// 		question.changeContent(request.getContent());
// 	}
//
// 	private void validatePersonAsked(Long memberId, Long studyOnceCommentId) {
// 		if (!isPersonWhoAskedComment(memberId, studyOnceCommentId)) {
// 			throw new CafegoryException(STUDY_ONCE_COMMENT_PERMISSION_DENIED);
// 		}
// 	}
//
// 	@Override
// 	public void deleteQuestion(Long studyOnceCommentId) {
// 		// 작성한 본인인지 검증하는 로직은 호출하는 쪽에서 한다. 관리자가 호출할 수 있기 때문이다.
// 		StudyOnceComment question = findStudyOnceCommentById(studyOnceCommentId);
// 		if (question.hasReply()) {
// 			throw new CafegoryException(STUDY_ONCE_PARENT_COMMENT_REMOVAL_BLOCKED);
// 		}
// 		studyOnceCommentRepository.delete(question);
// 	}
//
// 	private StudyOnceComment findStudyOnceCommentById(Long studyOnceCommentId) {
// 		return studyOnceCommentRepository.findById(studyOnceCommentId)
// 			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_COMMENT_NOT_FOUND));
// 	}
//
// 	private Member findMemberById(Long memberId) {
// 		return memberRepository.findById(memberId)
// 			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
// 	}
//
// 	private CafeStudy findStudyOnceById(long studyOnceId) {
// 		return studyOnceRepository.findById(studyOnceId)
// 			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
// 	}
}
