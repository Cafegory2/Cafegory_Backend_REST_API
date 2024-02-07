package com.example.demo.service;

import com.example.demo.dto.CafeResponse;

public interface CafeService {

	CafeResponse findCafeById(Long cafeId);
}
