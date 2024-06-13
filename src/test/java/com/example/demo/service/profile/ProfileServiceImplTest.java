package com.example.demo.service.profile;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.example.demo.config.TestConfig;
import com.example.demo.domain.cafe.Cafe;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.ThumbnailImage;
import com.example.demo.domain.study.StudyMember;
import com.example.demo.domain.study.StudyOnce;
import com.example.demo.dto.profile.ProfileUpdateRequest;
import com.example.demo.dto.profile.ProfileUpdateResponse;
import com.example.demo.dto.study.StudyOnceCreateRequest;
import com.example.demo.dto.study.StudyOnceCreateResponse;
import com.example.demo.exception.CafegoryException;
import com.example.demo.helper.save.CafeSaveHelper;
import com.example.demo.helper.save.MemberSaveHelper;
import com.example.demo.helper.save.StudyOnceSaveHelper;
import com.example.demo.helper.save.ThumbnailImageSaveHelper;
import com.example.demo.repository.study.StudyMemberRepository;
import com.example.demo.repository.study.StudyOnceRepository;
import com.example.demo.service.study.StudyOnceService;

@SpringBootTest
@Import({TestConfig.class})
@Transactional
class ProfileServiceImplTest {

	@Autowired
	private StudyOnceService studyOnceService;
	@Autowired
	private StudyOnceRepository studyOnceRepository;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private StudyMemberRepository studyMemberRepository;
	@Autowired
	private CafeSaveHelper cafePersistHelper;
	@Autowired
	private MemberSaveHelper memberPersistHelper;
	@Autowired
	private StudyOnceSaveHelper studyOncePersistHelper;
	@Autowired
	private ThumbnailImageSaveHelper thumbnailImagePersistHelper;

	@Test
	@DisplayName("자신이 스터디 장인 카공의 멤버면 프로필 조회 성공")
	void successWhenRequestMemberIsLeaderWithTargetMember() {
		long cafeId = cafePersistHelper.persistCafeWith24For7().getId();
		ThumbnailImage thumbnailImage = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		long requestMemberId = memberPersistHelper.persistDefaultMember(thumbnailImage).getId();
		long targetMemberId = memberPersistHelper.persistDefaultMember(thumbnailImage).getId();
		LocalDateTime start = LocalDateTime.now().plusHours(4);
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, start.plusHours(5), cafeId);
		StudyOnceCreateResponse study = studyOnceService.createStudy(requestMemberId, studyOnceCreateRequest,
			LocalDate.now());
		studyOnceService.tryJoin(targetMemberId, study.getStudyOnceId());
		Assertions.assertDoesNotThrow(() -> profileService.get(requestMemberId, targetMemberId));
	}

	@Test
	@DisplayName("자신이 참여 확정 상태인 카공의 멤버면 프로필 조회 성공")
	void successWhenRequestMemberAndTargetMemberJoinSameStudy() {
		Cafe cafe = cafePersistHelper.persistDefaultCafe();
		ThumbnailImage thumbnailImage = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		long requestMemberId = memberPersistHelper.persistDefaultMember(thumbnailImage).getId();
		long targetMemberId = memberPersistHelper.persistDefaultMember(thumbnailImage).getId();
		Member leader = memberPersistHelper.persistDefaultMember(thumbnailImage);
		StudyOnce studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);

		studyOnceService.tryJoin(targetMemberId, studyOnce.getId());
		studyOnceService.tryJoin(requestMemberId, studyOnce.getId());

		syncStudyOnceRepositoryAndStudyMemberRepository();
		Assertions.assertDoesNotThrow(
			() -> profileService.get(requestMemberId, targetMemberId, studyOnce.getStartDateTime()));
	}

	/**
	 * JPA 의 Cascade 가 InMemory 구현체에서는 작동하지 않으므로 수동 동기화가 필요함.
	 */
	private void syncStudyOnceRepositoryAndStudyMemberRepository() {
		List<StudyMember> allStudyMembers = studyOnceRepository.findAll().stream()
			.map(StudyOnce::getStudyMembers)
			.flatMap(Collection::stream)
			.collect(Collectors.toList());
		studyMemberRepository.saveAll(allStudyMembers);
	}

	private static StudyOnceCreateRequest makeStudyOnceCreateRequest(LocalDateTime start, LocalDateTime end,
		long cafeId) {
		return new StudyOnceCreateRequest(cafeId, "테스트 카페", start, end, 4, true, "오픈채팅방 링크");
	}

	@Test
	@DisplayName("자신의 프로필 조회 성공")
	void successWhenRequestSelf() {
		ThumbnailImage thumbnailImage = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		long requestMemberId = memberPersistHelper.persistDefaultMember(thumbnailImage).getId();
		Assertions.assertDoesNotThrow(
			() -> profileService.get(requestMemberId, requestMemberId));
	}

	@Test
	@DisplayName("프로필 조회 조건을 만족하지 않는 경우 실패")
	void failWhenOtherCase() {
		ThumbnailImage thumbnailImage = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		long requestMemberId = memberPersistHelper.persistDefaultMember(thumbnailImage).getId();
		long targetMemberId = memberPersistHelper.persistDefaultMember(thumbnailImage).getId();
		CafegoryException cafegoryException = Assertions.assertThrows(CafegoryException.class,
			() -> profileService.get(requestMemberId, targetMemberId));
		Assertions.assertEquals(cafegoryException.getMessage(), PROFILE_GET_PERMISSION_DENIED.getErrorMessage());
	}

	@Test
	@DisplayName("자신의 프로필을 수정하는 경우 성공")
	void updateSuccessWhenSelf() {
		ThumbnailImage thumbnailImage = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		long requestMemberId = memberPersistHelper.persistDefaultMember(thumbnailImage).getId();
		ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest("name", "introduction");
		ProfileUpdateResponse update = profileService.update(requestMemberId, requestMemberId, profileUpdateRequest);
		Assertions.assertEquals(update.getName(), "name");
	}

	@Test
	@DisplayName("타인의 프로필을 수정하는 경우 실패")
	void updateFailWhenOther() {
		ThumbnailImage thumbnailImage = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		long requestMemberId = memberPersistHelper.persistDefaultMember(thumbnailImage).getId();
		long targetMemberId = memberPersistHelper.persistDefaultMember(thumbnailImage).getId();
		ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest("name", "introduction");
		CafegoryException cafegoryException = Assertions.assertThrows(CafegoryException.class,
			() -> profileService.update(requestMemberId, targetMemberId, profileUpdateRequest));
		Assertions.assertEquals(cafegoryException.getMessage(), PROFILE_UPDATE_PERMISSION_DENIED.getErrorMessage());
	}
}
