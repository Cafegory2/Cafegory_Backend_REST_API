package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.domain.cafe.SnsDetail;
import com.example.demo.dto.cafe.CafeSearchSnsResponse;

public class SnsDetailMapper {

	public List<CafeSearchSnsResponse> toCafeSearchSnsResponses(List<SnsDetail> snsDetails) {
		return snsDetails.stream()
			.map(snsDetail -> new CafeSearchSnsResponse(snsDetail.getName(), snsDetail.getUrl()))
			.collect(Collectors.toList());
	}
}
