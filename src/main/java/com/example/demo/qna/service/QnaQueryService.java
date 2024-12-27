package com.example.demo.qna.service;

import org.springframework.stereotype.Service;

import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.implement.CommentReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaQueryService {

	private final CommentReader commentReader;

	public ChildComment getCommentOld(Long commentId) {
		return commentReader.readOld(commentId);
	}

	public Comment getComment(CommentId commentId) {
		return commentReader.read(commentId);
	}
}
