// package com.example.demo.mapper;
//
// import java.util.List;
// import java.util.stream.Collectors;
//
// import com.example.demo.domain.cafe.BusinessHour;
// import com.example.demo.dto.cafe.CafeSearchBusinessHourResponse;
//
// public class BusinessHourMapper {
//
// 	public List<CafeSearchBusinessHourResponse> toCafeSearchBusinessHourResponses(List<BusinessHour> businessHours) {
// 		return businessHours.stream()
// 			.map(hour -> new CafeSearchBusinessHourResponse(hour.getDay(), hour.getStartTime().toString(),
// 				hour.getEndTime().toString()))
// 			.collect(Collectors.toList());
// 	}
//
// }
