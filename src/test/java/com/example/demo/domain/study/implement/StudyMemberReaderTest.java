package com.example.demo.domain.study.implement;

import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static com.example.demo.persister.StudyMemberPersister.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.config.ServiceTest;
import com.example.demo.db.cafe.cafe.CafeEntity;
import com.example.demo.db.member.MemberEntity;
import com.example.demo.db.study.study.CafeStudyEntity;
import com.example.demo.domain.study.domain.StudyId;
import com.example.demo.domain.study.domain.StudyMemberId;

class StudyMemberReaderTest extends ServiceTest {

	@Autowired
	private StudyMemberReader sut;

	@Test
	@DisplayName("카공에 참여한 현재 인원수를 찾는다.")
	void find_current_participants() {
		//given
		CafeEntity cafe = aCafe().persistWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().persist();
		MemberEntity participant = aMember().asParticipant().persist();

		CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
		aStudyMember().withStudy(study).withMember(coordinator).asCoordinator().persist();
		aStudyMember().withStudy(study).withMember(participant).asParticipant().persist();
		//when
		int result = sut.loadParticipantCount(new StudyId(study.getId()));
		//then
		assertThat(result).isEqualTo(2);
	}

	@Test
	@DisplayName("카공에 참여한 사람들의 아이디를 찾는다.")
	void find_current_participantIds() {
		//given
		CafeEntity cafe = aCafe().persistWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().persist();
		MemberEntity participant = aMember().asParticipant().persist();

		CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
		aStudyMember().withStudy(study).withMember(coordinator).asCoordinator().persist();
		aStudyMember().withStudy(study).withMember(participant).asParticipant().persist();
		//when
		List<StudyMemberId> result = sut.readParticipantIdsBy(new StudyId(study.getId()));
		//then
		assertThat(result).hasSize(2);
	}
}
