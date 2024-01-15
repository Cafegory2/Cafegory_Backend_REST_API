package com.example.demo.service.dto;

import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 어떤 비즈니스 로직의 결과를 감싸는 Wrapper 클래스.
 * <p>
 *     비즈니스 로직 중, 정상 로직 수행 중에서 그 결과가 실패 혹은 성공일 수있는 경우, 실패 이유를 함께 전달할 수 있다.
 * </p>
 * @param <T>
 */
@RequiredArgsConstructor
public class LogicResult<T> {
	private final T result;
	private final String failCause;

	public LogicResult(@NonNull T result) {
		this.result = result;
		this.failCause = null;
	}

	public Optional<String> getFailCause() {
		return Optional.ofNullable(failCause);
	}

	public Optional<T> getResult() {
		return Optional.ofNullable(result);
	}
}
