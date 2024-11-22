package com.example.demo.study.implement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ServiceTest;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.util.TimeUtil;

class StudyEditorTest extends ServiceTest {

	@Autowired
	private StudyEditor sut;

	@Autowired
	private CafeSaveHelper cafeSaveHelper;
	@Autowired
	private CafeStudySaveHelper cafeStudySaveHelper;
	@Autowired
	private MemberSaveHelper memberSaveHelper;

	@Autowired
	private TimeUtil timeUtil;

	@Test
	@DisplayName("카공을 삭제할 때 관련된 데이터를 같이 삭제한다.")
	void remove_study_with_cascade() {
		//given
		CafeEntity cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();

		MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");
		LocalDateTime start = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);
		CafeStudyEntity study = cafeStudySaveHelper.saveCafeStudy(cafe, coordinator, start, start.plusHours(2));

		//when
		//then
		assertDoesNotThrow(
			() -> sut.removeWithCascade(study.getId(), coordinator.getId(), timeUtil.now())
		);
	}
}