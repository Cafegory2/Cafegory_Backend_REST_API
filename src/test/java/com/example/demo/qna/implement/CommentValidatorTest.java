package com.example.demo.qna.implement;


import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.MemberIdentity;
import com.example.demo.qna.domain.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.example.demo.exception.ExceptionType.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CommentValidatorTest {

    private CommentValidator sut = new CommentValidator();

    @Test
    @DisplayName("댓글 문자열 검증")
    void validate_not_blank1() {
        assertDoesNotThrow(() -> sut.validateContentNotBlank("텍스트"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("댓글, 빈 문자열 또는 공백 검증")
    void validate_not_blank2(String value) {
        assertThatThrownBy(() -> sut.validateContentNotBlank(value))
            .isInstanceOf(CafegoryException.class)
            .hasMessage(CAFE_STUDY_COMMENT_CONTENT_NOT_BLANK.getErrorMessage());
    }

    @Test
    @DisplayName("댓글 작성자와 수정을 요청한 사용자가 일치한다.")
    void success_validate_comment_author_() {
        Comment comment = Comment.builder()
            .author(MemberIdentity.builder().id(1L).build())
            .build();
        assertDoesNotThrow(() -> sut.validateCommentAuthor(comment, 1L));
    }

    //TODO 기존에서는 Service 레이어 테스트에서 "자신이 작성한 댓글만 수정할 수 있다" 테스트를 작성했는데, 공유한 테스트 작성법을 읽어보고 validator 테스트에서 테스트를 작성하고, Service 레이어의 자신이 작성한 테스트는 지워버렸다.
    @Test
    @DisplayName("댓글 작성자와 수정을 요청한 사용자가 일치하지 않는다.")
    void fail_validate_comment_author2() {
        Comment comment = Comment.builder()
            .author(MemberIdentity.builder().id(1L).build())
            .build();
        assertThatThrownBy(() -> sut.validateCommentAuthor(comment, 2L))
            .isInstanceOf(CafegoryException.class)
            .hasMessage(CAFE_STUDY_COMMENT_PERMISSION_DENIED.getErrorMessage());
    }
}
