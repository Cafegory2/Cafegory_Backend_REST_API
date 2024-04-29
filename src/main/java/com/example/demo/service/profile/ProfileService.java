package com.example.demo.service.profile;

import java.time.LocalDateTime;

import com.example.demo.dto.profile.ProfileGetResponse;
import com.example.demo.dto.profile.ProfileUpdateRequest;
import com.example.demo.dto.profile.ProfileUpdateResponse;

public interface ProfileService {
	default ProfileGetResponse get(Long requestMemberId, Long targetMemberId) {
		return get(requestMemberId, targetMemberId, LocalDateTime.now());
	}

	ProfileGetResponse get(Long requestMemberId, Long targetMemberId, LocalDateTime baseDateTime);

	ProfileUpdateResponse update(Long requestMemberId, Long targetMemberId, ProfileUpdateRequest profileUpdateRequest);
}
