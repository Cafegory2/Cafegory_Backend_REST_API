package com.example.demo.util;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MicroTimeUtilsTest {

	@Test
	@DisplayName("나노초에서 마이크로초로 변환한다.")
	void convert_nano_to_micro_time() {
		LocalTime result = MicroTimeUtils.toMicroTime(LocalTime.of(23, 59, 59, 999_999_999));
		assertThat(result).isEqualTo(LocalTime.of(23, 59, 59, 999_999_000));
	}

	@Test
	@DisplayName("나노초에서 마이크로초로 변환한다.")
	void convert_nano_to_micro_date_time() {
		LocalDateTime result = MicroTimeUtils.toMicroDateTime(LocalDateTime.MAX);
		assertThat(result).isEqualTo(LocalDateTime.MAX.withNano(999_999_000));
	}
}