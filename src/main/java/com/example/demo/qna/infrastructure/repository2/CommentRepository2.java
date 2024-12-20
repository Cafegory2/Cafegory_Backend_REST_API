package com.example.demo.qna.infrastructure.repository2;

import com.example.demo.qna.domain.ChildComment;
import com.example.demo.study.domain.StudyRole;

public interface CommentRepository2 {

	Long save(ChildComment comment, StudyRole studyRole);
}
