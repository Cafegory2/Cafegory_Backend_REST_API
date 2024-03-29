package com.example.demo.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileUpdateRequest {
	private String name;
	private String introduction;
}
