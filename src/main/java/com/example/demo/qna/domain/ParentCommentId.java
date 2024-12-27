package com.example.demo.qna.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ParentCommentId {

	private final Long id;

	public boolean isNull() {
		return id == null;
	}
}
