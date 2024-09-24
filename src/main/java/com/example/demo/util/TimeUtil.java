package com.example.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TimeUtil {

	LocalTime maxLocalTime();

	LocalDateTime now();
	//TODO localDate메서드 처럼 리팩터링?
	LocalDate localDate(int year, int month, int day);

	LocalTime truncateTimeToSecond(LocalTime time);

	LocalDateTime truncateDateTimeToSecond(LocalDateTime dateTime);
}
