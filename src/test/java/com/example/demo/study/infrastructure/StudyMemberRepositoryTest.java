package com.example.demo.study.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

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

	@Autowired
	private CafeSaveHelper cafeSaveHelper;
	@Autowired
	private CafeStudySaveHelper cafeStudySaveHelper;
	@Autowired
	private MemberSaveHelper memberSaveHelper;

	@Autowired
	private TimeUtil timeUtil;

	@Test
	void findByCafeStudy_IdAndMember_Id() {
		// given
		CafeEntity cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();

		MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");
		LocalDateTime start = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);
		CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, coordinator, start, start.plusHours(2));

		// when
		Optional<CafeStudyMemberEntity> cafeStudyMembers = sut.findByCafeStudy_IdAndMember_Id(cafeStudy.getId(),
			coordinator.getId());

		// then
		assertThat(cafeStudyMembers).isPresent();
	}
}
