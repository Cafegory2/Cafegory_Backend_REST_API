package com.example.demo.domain.qna.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ParentCommentId {

	private final Long id;

	public boolean isNull() {
		return id == null;
	}

	public boolean isSameId(ParentCommentId id) {
		return this.id.equals(id.getId());
	}
}
