// package com.example.demo.service.profile;
//
// import static com.example.demo.exception.ExceptionType.*;
//
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;
// import java.util.Set;
// import java.util.stream.Collectors;
//
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.example.demo.implement.member.Member;
// import com.example.demo.implement.study.CafeStudy;
// import com.example.demo.implement.study.CafeStudyMember;
// import com.example.demo.dto.profile.ProfileGetResponse;
// import com.example.demo.dto.profile.ProfileUpdateRequest;
// import com.example.demo.dto.profile.ProfileUpdateResponse;
// import com.example.demo.exception.CafegoryException;
// import com.example.demo.repository.member.MemberRepository;
// import com.example.demo.repository.study.StudyMemberRepository;
// import com.example.demo.repository.study.StudyOnceRepository;
//
// import lombok.RequiredArgsConstructor;
//
// @Transactional
// @Service
// @RequiredArgsConstructor
// public class ProfileServiceImpl implements ProfileService {
// 	private final MemberRepository memberRepository;
// 	private final StudyOnceRepository studyOnceRepository;
// 	private final StudyMemberRepository studyMemberRepository;
//
// 	@Override
// 	public ProfileGetResponse get(Long requestMemberId, Long targetMemberId, LocalDateTime baseDateTime) {
// 		if (isOwnerOfProfile(requestMemberId, targetMemberId)) {
// 			return makeProfileGetResponse(targetMemberId);
// 		}
// 		if (isAllowedCauseStudyLeader(requestMemberId, targetMemberId)) {
// 			return makeProfileGetResponse(targetMemberId);
// 		}
// 		if (isAllowedCauseSameStudyOnceMember(requestMemberId, targetMemberId, baseDateTime)) {
// 			return makeProfileGetResponse(targetMemberId);
// 		}
// 		throw new CafegoryException(PROFILE_GET_PERMISSION_DENIED);
// 	}
//
// 	@Override
// 	public ProfileUpdateResponse update(Long requestMemberId, Long targetMemberId,
// 		ProfileUpdateRequest profileUpdateRequest) {
// 		validateProfileUpdatePermission(requestMemberId, targetMemberId);
// 		Member targetMember = findTargetMember(targetMemberId);
// 		String name = profileUpdateRequest.getName();
// 		String introduction = profileUpdateRequest.getIntroduction();
// 		targetMember.updateProfile(name, introduction);
// 		return makeProfileUpdateResponse(targetMemberId);
// 	}
//
// 	private void validateProfileUpdatePermission(Long requestMemberId, Long targetMemberId) {
// 		if (!isOwnerOfProfile(requestMemberId, targetMemberId)) {
// 			throw new CafegoryException(PROFILE_UPDATE_PERMISSION_DENIED);
// 		}
// 	}
//
// 	private Member findTargetMember(Long targetMemberId) {
// 		Optional<Member> targetMember = memberRepository.findById(targetMemberId);
// 		if (targetMember.isEmpty()) {
// 			throw new CafegoryException(MEMBER_NOT_FOUND);
// 		}
// 		return targetMember.get();
// 	}
//
// 	private ProfileGetResponse makeProfileGetResponse(Long targetMemberID) {
// 		Member member = memberRepository.findById(targetMemberID).orElseThrow();
// 		return new ProfileGetResponse(member.getName(), member.getThumbnailImage().getThumbnailImage(),
// 			member.getIntroduction());
// 	}
//
// 	private ProfileUpdateResponse makeProfileUpdateResponse(Long targetMemberID) {
// 		Member member = memberRepository.findById(targetMemberID).orElseThrow();
// 		return new ProfileUpdateResponse(member.getName(), member.getThumbnailImage().getThumbnailImage(),
// 			member.getIntroduction());
// 	}
//
// 	private boolean isOwnerOfProfile(Long requestMemberID, Long targetMemberID) {
// 		if (requestMemberID == null || targetMemberID == null) {
// 			return false;
// 		}
// 		return requestMemberID.equals(targetMemberID);
// 	}
//
// 	private boolean isAllowedCauseStudyLeader(Long requestMemberID, Long targetMemberID) {
// 		List<CafeStudy> cafeStudyByLeaderID = findStudyOnceByLeaderID(requestMemberID);
// 		Set<Long> memberIdInStudyOnce = getMemberIdInStudyOnce(cafeStudyByLeaderID);
// 		return memberIdInStudyOnce.contains(targetMemberID);
// 	}
//
// 	private List<CafeStudy> findStudyOnceByLeaderID(Long requestMemberID) {
// 		return studyOnceRepository.findByLeaderId(requestMemberID);
// 	}
//
// 	private Set<Long> getMemberIdInStudyOnce(List<CafeStudy> byLeaderId) {
// 		return mapToMemberId(byLeaderId.stream()
// 			.flatMap(studyOnce -> studyOnce.getStudyMembers().stream())
// 			.collect(Collectors.toList()));
// 	}
//
// 	private static Set<Long> mapToMemberId(List<CafeStudyMember> cafeStudyMembers) {
// 		return cafeStudyMembers.stream()
// 			.map(CafeStudyMember::getMember)
// 			.map(Member::getId)
// 			.collect(Collectors.toSet());
// 	}
//
// 	private boolean isAllowedCauseSameStudyOnceMember(Long requestMemberID, Long targetMemberID, LocalDateTime base) {
// 		Member requestMember = memberRepository.findById(requestMemberID).orElseThrow();
// 		List<CafeStudyMember> cafeStudyMembers = findAllSameStudyOnceStudyMembersWith(requestMember, base);
// 		Set<Long> memberIdsThatJoinWithRequestMember = mapToMemberId(cafeStudyMembers);
// 		return memberIdsThatJoinWithRequestMember.contains(targetMemberID);
// 	}
//
// 	private List<CafeStudyMember> findAllSameStudyOnceStudyMembersWith(Member requestMember, LocalDateTime base) {
// 		LocalDate baseDate = LocalDate.from(base);
// 		return studyMemberRepository.findByMemberAndStudyDate(requestMember, baseDate)
// 			.stream()
// 			.map(CafeStudyMember::getStudy)
// 			.filter(studyOnce -> !studyOnce.canJoin(base))
// 			.filter(studyOnce -> !studyOnce.isEnd())
// 			.flatMap(studyOnce -> studyOnce.getStudyMembers().stream())
// 			.collect(Collectors.toList());
// 	}
// }
