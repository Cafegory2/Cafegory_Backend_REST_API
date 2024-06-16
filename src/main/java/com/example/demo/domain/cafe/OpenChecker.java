package com.example.demo.domain.cafe;

import java.time.LocalDateTime;
import java.util.List;

public interface OpenChecker<T> {

	boolean checkWithBusinessHours(List<T> hours, LocalDateTime now);

}
