package com.example.demo.qna.presentation;

import com.example.demo.config.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("카공 Qna 관련 테스트")
class QnaAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("비회원은 댓글을 작성할 수 없다.")
    void leave_comments() {
        //TODO 스프링 시큐리티 예외처리 리팩터링 후 테스트 코드 작성할 것
    }
}