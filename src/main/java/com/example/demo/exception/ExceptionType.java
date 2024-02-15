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
	STUDY_ONCE_WRONG_START_TIME(BAD_REQUEST, "카공 시작 시간은 현재 시간보다 최소 3시간 이후여야 합니다."),
	STUDY_ONCE_SHORT_DURATION(BAD_REQUEST, "카공 시간은 1시간 이상이어야 합니다."),
	STUDY_ONCE_LONG_DURATION(BAD_REQUEST, "카공 시간은 5시간 미만이어야 합니다."),
	STUDY_ONCE_TOO_MUCH_STUDY_IN_CAFE(BAD_REQUEST, "이 카페에 이 인원의 카공을 더이상 생성할 수 없습니다."),
	STUDY_ONCE_CONFLICT_TIME(CONFLICT, "해당 시간에 참여중인 카공이 이미 있습니다."),
	STUDY_ONCE_DUPLICATE(CONFLICT, "이미 참여중인 카공입니다."),
	STUDY_ONCE_FULL(CONFLICT, "카공 신청 가능 인원을 초과하였습니다."),
	STUDY_ONCE_NOT_FOUND(NOT_FOUND, "해당 카공을 찾을 수 없습니다."),
	STUDY_ONCE_TOO_LATE_JOIN(CONFLICT, "카공 인원 모집이 확정된 이후 참여 신청을 할 수 없습니다."),
	STUDY_ONCE_TOO_LATE_QUIT(CONFLICT, "카공 인원 모집이 확정된 이후 참여 취소를 할 수 없습니다."),
	STUDY_ONCE_TRY_QUIT_NOT_JOIN(CONFLICT, "참여중인 카공이 아닙니다."),
	STUDY_ONCE_LEADER_QUIT_FAIL(CONFLICT, "카공장은 다른 참여자가 있는 경우 참여 취소를 할 수 없습니다."),
	MEMBER_NOT_FOUND(NOT_FOUND, "없는 회원입니다."),
	CAFE_NOT_FOUND(NOT_FOUND, "없는 카페입니다."),
	REVIEW_NOT_FOUND(NOT_FOUND, "없는 리뷰입니다."),
	EMPTY_OR_WHITESPACE(BAD_REQUEST, "null, 빈 값, 혹은 공백만으로 이루어질 수 없습니다."),
	INVALID_NUMBER_RANGE(BAD_REQUEST, "숫자가 허용된 범위를 벗어났습니다."),
	REVIEW_OVER_CONTENT_SIZE(BAD_REQUEST, "리뷰 글자수가 200글자 이하여야 합니다."),
	REVIEW_INVALID_MEMBER(FORBIDDEN, "자신이 작성한 리뷰만 수정할 수 있습니다.");
	private final HttpStatus errStatus;
	private final String errorMessage;
}
