package com.example.demo.study.infrastructure;

import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static com.example.demo.persister.StudyMemberPersister.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.JpaTest;
import com.example.demo.member.infrastructure.MemberEntity;

class StudyMemberRepositoryTest extends JpaTest {

	@Autowired
	private StudyMemberRepository sut;

	@Test
	void findByCafeStudy_IdAndMember_Id() {
		// given
		CafeEntity cafe = aCafe().persistWith7daysFrom9To21();
		MemberEntity coordinator = aMember().asCoordinator().persist();

		CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
		aStudyMember().asCoordinator().withMember(coordinator).withStudy(study).persist();
		// when
		Optional<CafeStudyMemberEntity> cafeStudyMembers = sut.findByCafeStudy_IdAndMember_Id(study.getId(),
			coordinator.getId());
		// then
		assertThat(cafeStudyMembers).isPresent();
	}
}
