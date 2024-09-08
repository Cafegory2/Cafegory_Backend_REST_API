package com.example.demo.implement;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import com.example.demo.util.TimeUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CurrentDateTimeProvider implements DateTimeProvider {

	private final TimeUtil timeUtil;

	@Override
	public Optional<TemporalAccessor> getNow() {
		return Optional.of(timeUtil.now());
	}
}
