package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.domain.BusinessHour;
import com.example.demo.dto.BusinessHourResponse;

public class BusinessHourMapper {

	public List<BusinessHourResponse> entitiesToBusinessHourResponses(List<BusinessHour> businessHours) {
		return businessHours.stream()
			.map(hour -> new BusinessHourResponse(hour.getDay(), hour.getStartTime().toString(),
				hour.getEndTime().toString()))
			.collect(Collectors.toList());
	}
	
}
