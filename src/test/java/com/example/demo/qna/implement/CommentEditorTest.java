package com.example.demo.qna.implement;

import static com.example.demo.builder.CommentContentBuilder.*;
import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CommentPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ServiceTest;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CommentJpaRepository;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.util.TimeUtil;

class CommentEditorTest extends ServiceTest {

	@Autowired
	private CommentEditor sut;

	@Autowired
	private CommentJpaRepository cafeStudyCommentRepository;

	@Autowired
	private TimeUtil timeUtil;

	@Test
	@DisplayName("댓글을 저장한다.")
	void save_question() {
		//given
		CafeEntity cafe = aCafe().persist();
		MemberEntity coordinator = aMember().asCoordinator().persist();
		CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
		//when
		CommentId savedCommentId = sut.saveRootComment(
			aCommentContent().withContent("댓글 내용").build(),
			new StudyId(study.getId()), new MemberId(coordinator.getId()));
		//then
		assertThat(savedCommentId.getId()).isNotNull();
	}

	@Test
	@DisplayName("대댓글을 저장한다.")
	void save_reply() {
		//given
		CafeEntity cafe = aCafe().persist();

		MemberEntity coordinator = aMember().asCoordinator().persist();
		MemberEntity member = aMember().asParticipant().persist();

		CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
		CafeStudyCommentEntity rootComment = aComment().withStudy(study).withMember(member).persist();
		//when
		CommentId savedCommentId = sut.saveSubComment(
			aCommentContent().withContent("대댓글 내용").build(),
			new ParentCommentId(rootComment.getId()), new StudyId(study.getId()), new MemberId(member.getId()));
		//then
		assertThat(savedCommentId.getId()).isNotNull();
	}

	@Test
	@DisplayName("댓글을 수정한다.")
	void edit_comment() {
		//given
		CafeEntity cafe = aCafe().persistWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().persist();
		MemberEntity member = aMember().asParticipant().persist();

		CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
		CafeStudyCommentEntity commentEntity = aComment().withContent("테스트 댓글 내용")
			.withStudy(study)
			.withMember(member)
			.persist();
		//when
		sut.edit(
			aCommentContent().withContent("변경된 댓글 내용").build(), new CommentId(commentEntity.getId())
		);
		//then
		CafeStudyCommentEntity result = cafeStudyCommentRepository.findById(commentEntity.getId()).orElse(null);
		assertThat(result.getContent()).isEqualTo("변경된 댓글 내용");
	}

	@Test
	@DisplayName("댓글을 삭제한다.")
	void remove_comment() {
		//given
		CafeEntity cafe = aCafe().persistWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().persist();
		MemberEntity member = aMember().asParticipant().persist();

		CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
		CafeStudyCommentEntity comment = aComment().withStudy(study).withMember(member).persist();
		//when
		sut.remove(new CommentId(comment.getId()), timeUtil.now());
		//then
		List<CafeStudyCommentEntity> result = cafeStudyCommentRepository.findAll();
		assertThat(result).hasSize(0);
	}
}
