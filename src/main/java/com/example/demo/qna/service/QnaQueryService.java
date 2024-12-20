package com.example.demo.qna.service;

import org.springframework.stereotype.Service;

import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.implement.CommentReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaQueryService {

	private final CommentReader commentReader;

	public ChildComment getComment(Long commentId) {
		return commentReader.read(commentId);
	}
}
