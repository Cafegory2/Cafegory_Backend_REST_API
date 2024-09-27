package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TimeUtil {

	LocalTime maxLocalTime();

	LocalDateTime now();

	LocalTime localTime(int hour, int minute, int second);

	LocalDateTime localDateTime(int year, int month, int dayOfMonth, int hour, int minute, int second);
}
