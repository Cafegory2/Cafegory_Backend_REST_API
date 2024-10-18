package com.example.demo.service.profile;

import com.example.demo.dto.profile.WelcomeProfileResponse;
import com.example.demo.packageex.member.implement.MemberReader;
import com.example.demo.mapper.ProfileMapper;
import org.springframework.stereotype.Service;

import com.example.demo.implement.member.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberReader memberReader;
    private final ProfileMapper profileMapper;

    public WelcomeProfileResponse getWelcomeProfile(Long memberId) {
        Member member = memberReader.read(memberId);
        return profileMapper.toWelcomeProfileResponse(member);
    }


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
}
