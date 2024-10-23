package com.example.demo.implement.study;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.demo.implement.BaseEntity;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_study")
public class CafeStudy extends BaseEntity {
	//
	public static final int LIMIT_MEMBER_CAPACITY = 6;
	public static final int MIN_LIMIT_MEMBER_CAPACITY = 2;
	public static final int MIN_DELAY_BEFORE_START = 1 * 60 * 60;

	@Id
	@GeneratedValue
	@Column(name = "cafe_study_id")
	private Long id;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeEntity cafeEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coordinator_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberEntity coordinator;

	@Embedded
	private StudyPeriod studyPeriod;

	@Enumerated(EnumType.STRING)
	private MemberComms memberComms;

	private int maxParticipants;
	private String introduction;
	private int views;

	@Enumerated(EnumType.STRING)
	private RecruitmentStatus recruitmentStatus;

	@OneToMany(mappedBy = "cafeStudy")
	private List<CafeStudyMember> cafeStudyMembers = new ArrayList<>();

	@OneToMany(mappedBy = "cafeStudy")
	private List<CafeStudyCafeStudyTag> cafeStudyCafeStudyTags = new ArrayList<>();

	@Builder
	private CafeStudy(String name, CafeEntity cafeEntity, MemberEntity coordinator, StudyPeriod studyPeriod,
                      MemberComms memberComms, int maxParticipants, String introduction) {
		this.name = name;
		this.cafeEntity = cafeEntity;
		this.coordinator = coordinator;
		this.studyPeriod = studyPeriod;
		this.memberComms = memberComms;
		this.maxParticipants = maxParticipants;
		this.introduction = introduction;
		this.views = 0;
		this.recruitmentStatus = RecruitmentStatus.OPEN;

		addCoordinatorToStudy(coordinator);
	}

	private void addCoordinatorToStudy(MemberEntity coordinator) {
		CafeStudyMember cafeStudyMember = CafeStudyMember.builder()
			.cafeStudy(this)
			.member(coordinator)
			.studyRole(StudyRole.COORDINATOR)
			.build();
		cafeStudyMembers.add(cafeStudyMember);
	}

	public boolean isRecruitmentOpen() {
		return this.recruitmentStatus.isRecruitmentOpen();
	}

	// private void validateStartDateTime(LocalDateTime startDateTime) {
	// 	LocalDateTime now = LOCAL_DATE_TIME_NOW;
	// 	Duration between = Duration.between(now, startDateTime);
	// 	if (between.toSeconds() < 3 * 60 * 60) {
	// 		throw new CafegoryException(ExceptionType.STUDY_ONCE_WRONG_START_TIME);
	// 	}
	// }
	//
	// private void validateStudyOnceTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
	// 	Duration between = Duration.between(startDateTime, endDateTime);
	// 	if (between.toSeconds() < 60 * 60) {
	// 		LocalTime startLocalTime = startDateTime.toLocalTime();
	// 		LocalTime endLocalTime = endDateTime.toLocalTime();
	// 		validateStartAndEndTime(startLocalTime, endLocalTime);
	// 	}
	// 	if (between.toSeconds() > 5 * 60 * 60) {
	// 		throw new CafegoryException(STUDY_ONCE_LONG_DURATION);
	// 	}
	// }
	//
	// private void validateStartAndEndTime(LocalTime startLocalTime, LocalTime endLocalTime) {
	// 	if (!(startLocalTime.equals(LocalTime.of(23, 0)) && (endLocalTime.equals(MAX_LOCAL_TIME) || endLocalTime.equals(
	// 		LocalTime.of(23, 59, 59))))) {
	// 		throw new CafegoryException(STUDY_ONCE_SHORT_DURATION);
	// 	}
	// }
	//
	// private void validateMaxMemberCount(int maxMemberCount) {
	// 	if (maxMemberCount > LIMIT_MEMBER_CAPACITY || maxMemberCount < MIN_LIMIT_MEMBER_CAPACITY) {
	// 		throw new CafegoryException(STUDY_ONCE_LIMIT_MEMBER_CAPACITY);
	// 	}
	// }
	//
	// private void validateNowMemberCountOverMaxLimit(int nowMemberCount, int maxMemberCount) {
	// 	if (nowMemberCount > maxMemberCount) {
	// 		throw new CafegoryException(STUDY_ONCE_CANNOT_REDUCE_BELOW_CURRENT);
	// 	}
	// }
	//
	// public void tryJoin(Member member, LocalDateTime requestTime) {
	// 	validateJoinRequestTime(requestTime);
	// 	validateDuplicateJoin(member);
	// 	validateStudyScheduleConflict(member);
	// 	validateStudyMemberIsFull();
	// 	StudyMember studyMember = new StudyMember(member, this);
	// 	this.studyMembers.add(studyMember);
	// 	member.addStudyMember(studyMember);
	// 	this.nowMemberCount = this.studyMembers.size();
	// }
	//
	// private void validateStudyMemberIsFull() {
	// 	if (nowMemberCount == maxMemberCount) {
	// 		throw new CafegoryException(STUDY_ONCE_FULL);
	// 	}
	// }
	//
	// private void validateStudyScheduleConflict(Member member) {
	// 	boolean isStudyConflict = member.hasStudyScheduleConflict(this.startDateTime, this.endDateTime);
	// 	if (isStudyConflict) {
	// 		throw new CafegoryException(STUDY_ONCE_CONFLICT_TIME);
	// 	}
	// }
	//
	// private void validateDuplicateJoin(Member memberThatExpectedToJoin) {
	// 	boolean isAlreadyJoin = studyMembers.stream()
	// 		.anyMatch(studyMember -> studyMember.getMember().equals(memberThatExpectedToJoin));
	// 	if (isAlreadyJoin) {
	// 		throw new CafegoryException(STUDY_ONCE_DUPLICATE);
	// 	}
	// }
	//
	// private void validateJoinRequestTime(LocalDateTime requestTime) {
	// 	Duration between = Duration.between(requestTime, startDateTime);
	// 	if (between.toSeconds() < 3600) {
	// 		throw new CafegoryException(STUDY_ONCE_TOO_LATE_JOIN);
	// 	}
	// }
	//
	// public StudyMember tryQuit(Member memberThatExpectedToQuit, LocalDateTime requestTime) {
	// 	validateQuitRequestTime(requestTime);
	// 	StudyMember studyMember = studyMembers.stream()
	// 		.filter(s -> s.getMember().getId().equals(memberThatExpectedToQuit.getId()))
	// 		.findFirst()
	// 		.orElseThrow(() -> new CafegoryException(STUDY_ONCE_TRY_QUIT_NOT_JOIN));
	// 	studyMembers.remove(studyMember);
	// 	return studyMember;
	// }
	//
	// private void validateQuitRequestTime(LocalDateTime requestTime) {
	// 	Duration between = Duration.between(requestTime, startDateTime);
	// 	if (between.toSeconds() < 3600) {
	// 		throw new CafegoryException(STUDY_ONCE_TOO_LATE_QUIT);
	// 	}
	// }
	//
	// public void changeCafe(@NonNull Cafe cafe) {
	// 	this.cafe = cafe;
	// 	cafe.getStudyOnceGroup().add(this);
	// }
	//
	// public boolean isLeader(Member member) {
	// 	return leader.getId().equals(member.getId());
	// }
	//
	// public boolean canJoin(LocalDateTime baseDateTime) {
	// 	Duration between = Duration.between(truncateDateTimeToSecond(baseDateTime), startDateTime);
	// 	return between.toSeconds() >= 60 * 60;
	// }
	//
	// public void changeName(String name) {
	// 	validateEmptyOrWhiteSpace(name, STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);
	// 	this.name = name;
	// }
	//
	// public void changeStudyOnceTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
	// 	validateStartDateTime(startDateTime);
	// 	validateStudyOnceTime(startDateTime, endDateTime);
	// 	this.startDateTime = startDateTime;
	// 	this.endDateTime = endDateTime;
	// }
	//
	// public void changeMaxMemberCount(int maxMemberCount) {
	// 	validateMaxMemberCount(maxMemberCount);
	// 	validateNowMemberCountOverMaxLimit(this.nowMemberCount, maxMemberCount);
	// 	this.maxMemberCount = maxMemberCount;
	// }
	//
	// public void changeCanTalk(boolean ableToTalk) {
	// 	this.ableToTalk = ableToTalk;
	// }
	//
	// public boolean doesOnlyLeaderExist() {
	// 	return this.studyMembers.size() == 1 && this.studyMembers.get(0).isLeader(this.leader);
	// }
	//
	// public void changeOpenChatUrl(String openChatUrl) {
	// 	validateEmptyOrWhiteSpace(openChatUrl, STUDY_ONCE_OPEN_CHAT_URL_EMPTY_OR_WHITESPACE);
	// 	this.openChatUrl = openChatUrl;
	// }
	//
	// private void validateEmptyOrWhiteSpace(String target, ExceptionType exceptionType) {
	// 	if (StringUtils.isEmptyOrWhitespace(target)) {
	// 		throw new CafegoryException(exceptionType);
	// 	}
	// }
	//
	// public boolean isAttendance(Member member) {
	// 	return studyMembers.stream().anyMatch(s -> s.getId().getMemberId().equals(member.getId()));
	// }

}
