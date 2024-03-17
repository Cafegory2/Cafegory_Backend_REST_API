package com.example.demo.service;

import com.example.demo.dto.auth.CafegoryToken;

public interface AuthService {
	CafegoryToken refresh(String refreshToken);
}
