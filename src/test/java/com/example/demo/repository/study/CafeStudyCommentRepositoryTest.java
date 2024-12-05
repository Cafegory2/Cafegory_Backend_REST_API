package com.example.demo.repository.study;

import static com.example.demo.testbuilder.CafeBuilder.*;
import static com.example.demo.testbuilder.CommentBuilder.*;
import static com.example.demo.testbuilder.MemberBuilder.*;
import static com.example.demo.testbuilder.StudyBuilder.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.testbuilder.CafeBuilder;
import com.example.demo.testbuilder.CommentBuilder;
import com.example.demo.testbuilder.MemberBuilder;
import com.example.demo.testbuilder.StudyBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.JpaTest;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudyCommentSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.util.TimeUtil;

class CafeStudyCommentRepositoryTest extends JpaTest {

	@Autowired
	private CafeStudyCommentRepository sut;

	@Autowired
	private MemberSaveHelper memberSaveHelper;
	@Autowired
	private CafeSaveHelper cafeSaveHelper;
	@Autowired
	private CafeStudySaveHelper cafeStudySaveHelper;
	@Autowired
	private CafeStudyCommentSaveHelper cafeStudyCommentSaveHelper;
	@Autowired
	private TimeUtil timeUtil;

	@Test
	@DisplayName("카공 ID를 사용하여 해당 카공에 달린 모든 댓글과 대댓글을 조회한다.")
	void find_all_comments_with_replies_in_cafe_study() {
		//given
		MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com", "카공글 작성자");
		MemberEntity member1 = memberSaveHelper.saveMember("test1@gmail.com", "멤버1");
		MemberEntity member2 = memberSaveHelper.saveMember("test2@gmail.com", "멤버2");

		CafeEntity cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();

		LocalDateTime startDateTime = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);

		CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, coordinator, startDateTime,
			startDateTime.plusHours(2));

		CafeStudyCommentEntity root1 = cafeStudyCommentSaveHelper.saveRootComment(member1, StudyRole.MEMBER, cafeStudy);
		CafeStudyCommentEntity replyToRoot1 = cafeStudyCommentSaveHelper.saveReplyToParentComment(root1, coordinator,
			StudyRole.COORDINATOR, cafeStudy);
		CafeStudyCommentEntity replyToReply1 = cafeStudyCommentSaveHelper.saveReplyToParentComment(replyToRoot1,
			member1, StudyRole.MEMBER, cafeStudy);

		CafeStudyCommentEntity root2 = cafeStudyCommentSaveHelper.saveRootComment(member2, StudyRole.MEMBER, cafeStudy);
		CafeStudyCommentEntity replyToRoot2 = cafeStudyCommentSaveHelper.saveReplyToParentComment(root2, coordinator,
			StudyRole.COORDINATOR, cafeStudy);
		CafeStudyCommentEntity replyToReply2 = cafeStudyCommentSaveHelper.saveReplyToParentComment(replyToRoot2,
			member1, StudyRole.MEMBER, cafeStudy);
		//when
		List<CafeStudyCommentEntity> result = sut.findAllBy(cafeStudy.getId());
		//then
		assertThat(result.size()).isEqualTo(6);
	}

	@Test
	@DisplayName("카공 ID를 사용하여 해당 카공에 달린 모든 댓글과 대댓글을 조회한다.")
	void find_all_comments_with_replies_in_cafe_study2() {
		//given
		MemberEntity coordinator = aMember().whoIsCoordinator().save();
		CafeStudyEntity study = aStudy()
			.with(aCafe().saveWith7daysFrom9To21())
			.with(coordinator)
			.withStudyPeriodFrom10To12().save();

		MemberEntity member1 = aMember().whoIsParticipant(1).save();
		MemberEntity member2 = aMember().whoIsParticipant(2).save();

		CommentBuilder comment = aComment().with(study);
		CafeStudyCommentEntity root1 = comment.but().withMember(member1).save();
		CafeStudyCommentEntity reply1ToRoot1 = comment.but().replyTo(root1).withCoordinator(coordinator).save();
		CafeStudyCommentEntity reply2ToReply1 = comment.but().replyTo(reply1ToRoot1).withMember(member2).save();

		CafeStudyCommentEntity root2 = comment.but().withMember(member2).save();
		CafeStudyCommentEntity reply1ToRoot2 = comment.but().replyTo(root2).withCoordinator(coordinator).save();
		CafeStudyCommentEntity reply2ToReply2 = comment.but().replyTo(reply1ToRoot2).withMember(member1).save();
		//when
		List<CafeStudyCommentEntity> result = sut.findAllBy(study.getId());
		//then
		assertThat(result.size()).isEqualTo(6);
	}
}