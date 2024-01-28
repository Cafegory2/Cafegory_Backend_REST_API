package com.example.demo.controller.mapper;

import com.example.demo.controller.dto.CafeSearchRequest;
import com.example.demo.service.dto.ServiceCafeSearchRequest;

public class ControllerCafeMapper {

	public ServiceCafeSearchRequest convertToServiceCafeSearchRequest(CafeSearchRequest searchRequest) {
		return ServiceCafeSearchRequest.of(
			searchRequest.isCanStudy(),
			searchRequest.getArea(),
			searchRequest.getMaxTime(),
			searchRequest.getMinBeveragePrice(),
			searchRequest.getPage(),
			searchRequest.getSizePerPage()
		);
	}
}
