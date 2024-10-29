package com.example.demo.qna.service;

import com.example.demo.exception.CafegoryException;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.implement.CommentEditor;
import com.example.demo.qna.implement.CommentReader;
import com.example.demo.qna.implement.CommentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.demo.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class QnaService {

    @Autowired
    private final CommentEditor commentEditor;
    @Autowired
    private final CommentReader commentReader;
    @Autowired
    private final CommentValidator commentValidator;

    public Comment leaveComment(Comment comment, Long memberId) {
        Long commentId = commentEditor.append(comment, memberId);
        return commentReader.read(commentId);
    }

    public void editComment(Comment comment, Long memberId) {
        Comment readComment = commentReader.read(comment.getCommentId());
        commentValidator.validateCommentAuthor(readComment, memberId);
        validateNoReplies(readComment);

        commentEditor.edit(readComment);
    }

    //TODO Validator에게 검증의 책임을 준다면 Validator는 DB계층을 의존해도 되나?
    private void validateNoReplies(Comment comment) {
        if(commentReader.existsReplies(comment.getCommentId())) {
            throw new CafegoryException(CAFE_STUDY_COMMENT_HAS_REPLY);
        }
    }

    public void remove(Long commentId, Long memberId, LocalDateTime now) {
        Comment readComment = commentReader.read(commentId);
        commentValidator.validateCommentAuthor(readComment, memberId);
        validateNoReplies(readComment);

        commentEditor.remove(commentId, now);
    }
}
