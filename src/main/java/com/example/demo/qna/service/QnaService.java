package com.example.demo.qna.service;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.implement.CommentEditor;
import com.example.demo.qna.implement.CommentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QnaService {

    @Autowired
    private final CommentEditor commentEditor;
    @Autowired
    private final CommentReader commentReader;

    @Transactional
    public Comment leaveComment(Comment comment, Long memberId) {
        Long commentId = commentEditor.append(comment, memberId);
        return commentReader.read(commentId);
    }

    @Transactional
    public void editComment(String content, Long commentId) {
        commentEditor.edit(content, commentId);
    }
}
