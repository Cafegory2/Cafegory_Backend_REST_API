package com.example.demo.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionType {
	JWT_EXPIRED(UNAUTHORIZED, "JWT 토큰이 만료되었습니다."),
	JWT_DESTROYED(UNAUTHORIZED, "JWT 토큰이 잘못되었습니다."),
	TOKEN_NOT_FOUND(UNAUTHORIZED, "토큰이 없습니다."),
	TOKEN_REFRESH_REJECT(UNAUTHORIZED, "토큰을 재발행할 수 없습니다."),
	STUDY_ONCE_CREATE_BETWEEN_CAFE_BUSINESS_HOURS(BAD_REQUEST, "카공 생성시 시작시간과 종료시간은 카페 영업시간내에 포함되어야 합니다."),
	STUDY_ONCE_WRONG_START_TIME(BAD_REQUEST, "카공 시작 시간은 현재 시간보다 최소 3시간 이후여야 합니다."),
	STUDY_ONCE_SHORT_DURATION(BAD_REQUEST, "카공 시간은 1시간 이상이어야 합니다."),
	STUDY_ONCE_LONG_DURATION(BAD_REQUEST, "카공 시간은 5시간 미만이어야 합니다."),
	STUDY_ONCE_TOO_MUCH_STUDY_IN_CAFE(BAD_REQUEST, "이 카페에 이 인원의 카공을 더이상 생성할 수 없습니다."),
	STUDY_ONCE_LIMIT_MEMBER_CAPACITY(BAD_REQUEST, "카공 최대 참여 인원 수는 5명 입니다."),
	STUDY_ONCE_CANNOT_REDUCE_BELOW_CURRENT(BAD_REQUEST, "카공 최대 참여 인원 수는 현재 참여 신청중인 인원보다 적을 수 없습니다."),
	STUDY_ONCE_CONFLICT_TIME(CONFLICT, "해당 시간에 참여중인 카공이 이미 있습니다."),
	STUDY_ONCE_DUPLICATE(CONFLICT, "이미 참여중인 카공입니다."),
	STUDY_ONCE_FULL(CONFLICT, "카공 신청 가능 인원을 초과하였습니다."),
	STUDY_ONCE_NOT_FOUND(NOT_FOUND, "해당 카공을 찾을 수 없습니다."),
	STUDY_ONCE_TOO_LATE_JOIN(CONFLICT, "카공 인원 모집이 확정된 이후 참여 신청을 할 수 없습니다."),
	STUDY_ONCE_TOO_LATE_QUIT(CONFLICT, "카공 인원 모집이 확정된 이후 참여 취소를 할 수 없습니다."),
	STUDY_ONCE_TRY_QUIT_NOT_JOIN(CONFLICT, "참여중인 카공이 아닙니다."),
	STUDY_ONCE_LEADER_QUIT_FAIL(CONFLICT, "카공장은 다른 참여자가 있는 경우 참여 취소를 할 수 없습니다."),
	STUDY_ONCE_INVALID_LEADER(BAD_REQUEST, "스터디 리더가 아닙니다."),
	STUDY_ONCE_LEADER_PERMISSION_DENIED(FORBIDDEN, "스터디 리더만 권한이 있습니다."),
	STUDY_ONCE_LOCATION_CHANGE_PERMISSION_DENIED(FORBIDDEN, "스터디 리더만 장소 변경을 할 권한이 있습니다."),
	STUDY_ONCE_REPLY_PERMISSION_DENIED(FORBIDDEN, "스터디 리더만 답변을 할 권한이 있습니다."),
	STUDY_ONCE_SINGLE_REPLY_PER_QUESTION(CONFLICT, "하나의 카공 질문에는 하나의 답변만 할 수 있습니다."),
	STUDY_ONCE_EARLY_TAKE_ATTENDANCE(BAD_REQUEST, "스터디 출석체크는 스터디 시작 10분 이후여야 합니다."),
	STUDY_ONCE_LATE_TAKE_ATTENDANCE(BAD_REQUEST, "스터디 출석체크는 스터디 진행시간 절반이 지나기전에만 변경할 수 있습니다. "),
	STUDY_ONCE_COMMENT_NOT_FOUND(NOT_FOUND, "없는 카공 댓글입니다."),
	STUDY_ONCE_COMMENT_PERMISSION_DENIED(FORBIDDEN, "댓글을 작성한 회원 본인만 수정 할 권한이 있습니다."),
	STUDY_ONCE_PARENT_COMMENT_MODIFICATION_BLOCKED(FORBIDDEN, "답변이 존재하는 질문은 수정 할 수 없습니다."),
	STUDY_ONCE_PARENT_COMMENT_REMOVAL_BLOCKED(FORBIDDEN, "답변이 존재하는 질문은 삭제 할 수 없습니다."),
	STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE(BAD_REQUEST, "스터디 이름은 null, 빈 값, 혹은 공백만으로 이루어질 수 없습니다."),
	STUDY_ONCE_OPEN_CHAT_URL_EMPTY_OR_WHITESPACE(BAD_REQUEST, "스터디의 오픈채팅방 URL은 null, 빈 값, 혹은 공백만으로 이루어질 수 없습니다."),
	MEMBER_NOT_FOUND(NOT_FOUND, "없는 회원입니다."),
	REVIEW_NOT_FOUND(NOT_FOUND, "없는 리뷰입니다."),
	REVIEW_OVER_CONTENT_SIZE(BAD_REQUEST, "리뷰 글자수가 최대 글자수 이하여야 합니다."),
	REVIEW_INVALID_MEMBER(FORBIDDEN, "자신이 작성한 리뷰만 수정할 수 있습니다."),
	REVIEW_CONTENT_EMPTY_OR_WHITESPACE(BAD_REQUEST, "리뷰 내용은 null, 빈 값, 혹은 공백만으로 이루어질 수 없습니다."),
	REVIEW_INVALID_RATE_RANGE(BAD_REQUEST, "평점이 허용된 범위를 벗어났습니다."),
	CAFE_NOT_FOUND(NOT_FOUND, "없는 카페입니다."),
	CAFE_INVALID_BUSINESS_TIME_RANGE(BAD_REQUEST, "영업시간이 허용된 범위를 벗어났습니다."),
	CAFE_NOT_FOUND_DAY_OF_WEEK(INTERNAL_SERVER_ERROR, "현재 요일과 일치하는 요일을 찾을 수 없습니다."),
	STUDY_MEMBER_NOT_FOUND(NOT_FOUND, "없는 스터디멤버입니다."),
	PROFILE_GET_PERMISSION_DENIED(FORBIDDEN, "프로필을 조회할 권한이 없는 상대입니다."),
	PROFILE_UPDATE_PERMISSION_DENIED(FORBIDDEN, "자신의 프로필만 조회할 수 있습니다."),
	PROFILE_UPDATE_INVALID_INTRODUCTION(FORBIDDEN, "자기 소개글은 300자 이하로만 작성할 수 있습니다.");

	private final HttpStatus errStatus;
	private final String errorMessage;
}
