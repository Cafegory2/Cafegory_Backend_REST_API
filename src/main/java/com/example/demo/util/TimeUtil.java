package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TimeUtil {

	LocalTime maxLocalTime();

	LocalDateTime now();

	LocalTime toSecond(LocalTime time);

	LocalDateTime toSecond(LocalDateTime dateTime);
}