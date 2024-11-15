package com.example.demo.trash.dto.email;

import java.util.List;

import lombok.Getter;

@Getter
public class CustomEmailSendRequest {

	private String title;
	private String content;
	private List<Long> memberIds;

	public CustomEmailSendRequest() {
	}

	public CustomEmailSendRequest(String title, String content, List<Long> memberIds) {
		this.title = title;
		this.content = content;
		this.memberIds = memberIds;
	}
}
