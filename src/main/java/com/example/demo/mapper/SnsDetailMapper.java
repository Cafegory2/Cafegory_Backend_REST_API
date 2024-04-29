package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.domain.cafe.SnsDetail;
import com.example.demo.dto.cafe.CafeSearchSnsResponse;
import com.example.demo.dto.cafe.SnsResponse;

public class SnsDetailMapper {

	public List<SnsResponse> toSnsResponses(List<SnsDetail> snsDetails) {
		return snsDetails.stream()
			.map(snsDetail -> new SnsResponse(snsDetail.getName(), snsDetail.getUrl()))
			.collect(Collectors.toList());
	}

	public List<CafeSearchSnsResponse> toCafeSearchSnsResponses(List<SnsDetail> snsDetails) {
		return snsDetails.stream()
			.map(snsDetail -> new CafeSearchSnsResponse(snsDetail.getName(), snsDetail.getUrl()))
			.collect(Collectors.toList());
	}
}
