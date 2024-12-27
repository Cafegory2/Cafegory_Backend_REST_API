package com.example.demo.qna.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;
import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.qna.infrastructure.repository2.CommentRepository2;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentEditor {

    private final CommentRepository2 commentRepository2;

    private final CafeStudyCommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final CafeStudyRepository cafeStudyRepository;

    private final CommentValidator commentValidator;

    public Long saveRootComment(
            CommentContent content, StudyId studyId, MemberId memberId
    ) {
        commentValidator.validateContentNotBlank(content.getContent());
        // TODO: StudyReader Entity 도입할 때 수정할 것
        return commentRepository2.saveRootComment(content, studyId, memberId);
    }

    public Long saveChildComment(
            CommentContent content, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId) {
        commentValidator.validateContentNotBlank(content.getContent());
        return commentRepository2.saveChildComment(content, parentCommentId, studyId, memberId);
    }

//	public Long save(ChildComment comment, Long memberId) {
//		commentValidator.validateContentNotBlank(comment.getContent());
//
//		MemberEntity author = memberRepository.findById(memberId)
//			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
//		CafeStudyCommentEntity parentComment = findParentCommentEntity(comment.getParentCommentId().getId());
//		CafeStudyEntity cafeStudy = cafeStudyRepository.findById(comment.getCafeStudyId())
//			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
//
//		CafeStudyCommentEntity commentEntity = createCafeStudyCommentEntity(comment.getContent(), author, parentComment,
//			cafeStudy);
//
//		// TODO: StudyReader Entity 도입할 때 수정할 것
//		return commentRepository2.save(comment, StudyRole.MEMBER);
//		// CafeStudyCommentEntity saved = commentRepository.save(commentEntity);
//
//		// return saved.getId();
//	}

    private CafeStudyCommentEntity findParentCommentEntity(Long parentCommentId) {
        if (parentCommentId == null) {
            return null;
        }
        return findCommentEntity(parentCommentId);
    }

    private CafeStudyCommentEntity findCommentEntity(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CafegoryException(CAFE_STUDY_COMMENT_NOT_FOUND));
    }

    private CafeStudyCommentEntity createCafeStudyCommentEntity(
            String content, MemberEntity author, CafeStudyCommentEntity parentComment, CafeStudyEntity cafeStudy) {
        return CafeStudyCommentEntity.builder()
                .author(author)
                .content(content)
                .parentComment(parentComment)
                .cafeStudy(cafeStudy)
                .build();
    }

    @Transactional
    public void edit(ChildComment comment) {
        commentValidator.validateContentNotBlank(comment.getContent());

        CafeStudyCommentEntity commentEntity = findCommentEntity(comment.getCommentId().getId());
        commentEntity.changeContent(comment.getContent());
    }

    @Transactional
    public void remove(Long commentId, LocalDateTime now) {
        CafeStudyCommentEntity commentEntity = findCommentEntity(commentId);
        commentEntity.softDelete(now);
    }
}
