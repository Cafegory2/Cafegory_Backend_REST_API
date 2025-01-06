package com.example.demo.domain.qna.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.qna.domain.Comment;
import com.example.demo.domain.qna.domain.CommentId;
import com.example.demo.domain.qna.implement.CommentReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaQueryService {

	private final CommentReader commentReader;

	public Comment getComment(CommentId commentId) {
		return commentReader.read(commentId);
	}
}
