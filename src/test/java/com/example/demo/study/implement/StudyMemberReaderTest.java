package com.example.demo.study.implement;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ServiceTest;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudyMemberSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.util.TimeUtil;

class StudyMemberReaderTest extends ServiceTest {

	@Autowired
	private StudyMemberReader sut;

	@Autowired
	private CafeSaveHelper cafeSaveHelper;
	@Autowired
	private CafeStudySaveHelper cafeStudySaveHelper;
	@Autowired
	private MemberSaveHelper memberSaveHelper;
	@Autowired
	private CafeStudyMemberSaveHelper cafeStudyMemberSaveHelper;
	@Autowired
	private TimeUtil timeUtil;

	@Test
	@DisplayName("카공에 참여한 현재 인원수를 찾는다.")
	void find_current_participants() {
		//given
		CafeEntity cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();

		MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");
		MemberEntity member = memberSaveHelper.saveMember("member@gmail.com");
		LocalDateTime start = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);
		CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, coordinator, start, start.plusHours(2));

		cafeStudyMemberSaveHelper.saveCafeStudyMember(cafeStudy, member);
		//when
		int result = sut.loadParticipantCount(cafeStudy.getId());
		//then
		assertThat(result).isEqualTo(2);
	}

	@Test
	@DisplayName("카공에 참여한 사람들의 아이디를 찾는다.")
	void find_current_participantIds() {
		//given
		CafeEntity cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();

		MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");
		MemberEntity member = memberSaveHelper.saveMember("member@gmail.com");
		LocalDateTime start = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);
		CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, coordinator, start, start.plusHours(2));

		cafeStudyMemberSaveHelper.saveCafeStudyMember(cafeStudy, member);
		//when
		List<Long> result = sut.readParticipantIdsBy(cafeStudy.getId());
		//then

		assertThat(result).hasSize(2);
	}

}