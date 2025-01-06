package com.example.demo.domain.qna.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentId {

	private final Long id;

	public boolean isSameId(CommentId id) {
		return this.id.equals(id.getId());
	}
}
