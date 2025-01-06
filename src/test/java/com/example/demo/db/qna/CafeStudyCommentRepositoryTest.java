package com.example.demo.db.qna;

import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CommentPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.config.JpaTest;
import com.example.demo.db.member.MemberEntity;
import com.example.demo.db.study.study.CafeStudyEntity;
import com.example.demo.persister.CommentPersister;

class CafeStudyCommentRepositoryTest extends JpaTest {

	@Autowired
	private CommentJpaRepository sut;

	@Test
	@DisplayName("카공 ID를 사용하여 해당 카공에 달린 모든 댓글과 대댓글을 조회한다.")
	void find_all_comments_with_replies_in_cafe_study2() {
		//given
		MemberEntity coordinator = aMember().asCoordinator().persist();
		CafeStudyEntity study = aStudy()
			.withCafe(aCafe().persistWith7daysFrom9To21())
			.withMember(coordinator)
			.withStudyPeriodFrom10To12().persist();

		MemberEntity member1 = aMember().asParticipant(1).persist();
		MemberEntity member2 = aMember().asParticipant(2).persist();

		CommentPersister comment = aComment().withStudy(study);
		CafeStudyCommentEntity root1 = comment.but().withMember(member1).persist();
		CafeStudyCommentEntity reply1ToRoot1 = comment.but().replyTo(root1).withCoordinator(coordinator).persist();
		CafeStudyCommentEntity reply2ToReply1 = comment.but().replyTo(reply1ToRoot1).withMember(member2).persist();

		CafeStudyCommentEntity root2 = comment.but().withMember(member2).persist();
		CafeStudyCommentEntity reply1ToRoot2 = comment.but().replyTo(root2).withCoordinator(coordinator).persist();
		CafeStudyCommentEntity reply2ToReply2 = comment.but().replyTo(reply1ToRoot2).withMember(member1).persist();
		//when
		List<CafeStudyCommentEntity> result = sut.findAllBy(study.getId());
		//then
		assertThat(result.size()).isEqualTo(6);
	}
}