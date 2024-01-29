package com.example.demo.domain.auth;

import org.springframework.util.MultiValueMap;

import com.example.demo.dto.auth.CafegoryToken;

public interface CafegoryTokenManager {
	CafegoryToken createToken(MultiValueMap<String, String> memberInformation);
}
