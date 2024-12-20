package com.example.demo.qna.infrastructure.repository2;

import com.example.demo.qna.domain.Comment;
import com.example.demo.study.domain.StudyRole;

import java.util.Optional;

public interface CommentRepository2 {

    Long save(Comment comment, StudyRole studyRole);
}
