package com.example.demo.util;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TruncatedTimeUtilTest {

	@Test
	@DisplayName("나노초에서 초단위로 절삭한다.")
	void convert_nano_to_micro_time() {
		LocalTime result = TruncatedTimeUtil.truncateTimeToSecond(LocalTime.of(23, 59, 59, 999_999_999));
		assertThat(result).isEqualTo(LocalTime.of(23, 59, 59));
	}

	@Test
	@DisplayName("나노초에서 초단위로 절삭한다.")
	void convert_nano_to_micro_date_time() {
		LocalDateTime result = TruncatedTimeUtil.truncateDateTimeToSecond(LocalDateTime.MAX);
		assertThat(result).isEqualTo(LocalDateTime.of(999999999, 12, 31, 23, 59, 59));
	}
}