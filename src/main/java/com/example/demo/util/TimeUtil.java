package com.example.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TimeUtil {

	LocalTime maxLocalTime();

	LocalDateTime now();

	LocalDate localDate(int year, int month, int day);

	LocalTime localTime(int hour, int minute, int second);

	LocalDateTime localDateTime(int year, int month, int dayOfMonth, int hour, int minute, int second);
}
