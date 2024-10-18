package com.example.demo.packageex.studyqna.implement;

import com.example.demo.packageex.studyqna.domain.CafeStudyComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CafeStudyQnaProcessor {

    private final CafeStudyCommentEditor commentEditor;
    private final CafeStudyCommentValidator commentValidator;

    public Long leaveComment(CafeStudyComment comment, Long memberId) {
        commentValidator.validate();
        return commentEditor.save(comment, memberId);
    }

}
