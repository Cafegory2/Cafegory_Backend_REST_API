package com.example.demo.study.infrastructure;

import static com.example.demo.testbuilder.CafeBuilder.*;
import static com.example.demo.testbuilder.MemberBuilder.*;
import static com.example.demo.testbuilder.StudyBuilder.*;
import static com.example.demo.testbuilder.StudyMemberBuilder.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.demo.testbuilder.CafeBuilder;
import com.example.demo.testbuilder.MemberBuilder;
import com.example.demo.testbuilder.StudyBuilder;
import com.example.demo.testbuilder.StudyMemberBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.JpaTest;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.util.TimeUtil;

class StudyMemberRepositoryTest extends JpaTest {

	@Autowired
	private StudyMemberRepository sut;

	@Test
	void findByCafeStudy_IdAndMember_Id() {
		// given
		CafeEntity cafe = aCafe().saveWith7daysFrom9To21();
		MemberEntity coordinator = aMember().asCoordinator().save();

		CafeStudyEntity study = aStudy().with(cafe).with(coordinator).save();
		aStudyMember().asCoordinator().with(coordinator).with(study).save();
		// when
		Optional<CafeStudyMemberEntity> cafeStudyMembers = sut.findByCafeStudy_IdAndMember_Id(study.getId(),
			coordinator.getId());
		// then
		assertThat(cafeStudyMembers).isPresent();
	}
}
