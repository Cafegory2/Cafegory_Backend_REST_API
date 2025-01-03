package com.example.demo.auth.dto.email;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailSendCustomTemplateResponse {
	private final String title;
	private final String content;
}
