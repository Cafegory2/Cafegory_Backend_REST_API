package com.example.demo.qna.service;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.implement.CommentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaQueryService {

    private final CommentReader commentReader;

    public Comment getComment(Long commentId) {
        return commentReader.read(commentId);
    }
}
