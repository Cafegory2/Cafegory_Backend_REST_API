package com.example.demo.study.implement;

import static com.example.demo.testbuilder.CafeBuilder.*;
import static com.example.demo.testbuilder.MemberBuilder.*;
import static com.example.demo.testbuilder.StudyBuilder.*;
import static com.example.demo.testbuilder.StudyMemberBuilder.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ServiceTest;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;

class StudyMemberReaderTest extends ServiceTest {

	@Autowired
	private StudyMemberReader sut;

	@Test
	@DisplayName("카공에 참여한 현재 인원수를 찾는다.")
	void find_current_participants() {
		//given
		CafeEntity cafe = aCafe().saveWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().save();
		MemberEntity participant = aMember().asParticipant().save();

		CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).save();
		aStudyMember().withStudy(study).withMember(coordinator).asCoordinator().save();
		aStudyMember().withStudy(study).withMember(participant).asParticipant().save();
		//when
		int result = sut.loadParticipantCount(study.getId());
		//then
		assertThat(result).isEqualTo(2);
	}

	@Test
	@DisplayName("카공에 참여한 사람들의 아이디를 찾는다.")
	void find_current_participantIds() {
		//given
		CafeEntity cafe = aCafe().saveWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().save();
		MemberEntity participant = aMember().asParticipant().save();

		CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).save();
		aStudyMember().withStudy(study).withMember(coordinator).asCoordinator().save();
		aStudyMember().withStudy(study).withMember(participant).asParticipant().save();
		//when
		List<Long> result = sut.readParticipantIdsBy(study.getId());
		//then
		assertThat(result).hasSize(2);
	}
}
