package com.example.demo.study.implement;

import com.example.demo.config.FakeTimeUtil;
import com.example.demo.exception.CafegoryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.example.demo.exception.ExceptionType.CAFE_STUDY_INVALID_NAME;
import static com.example.demo.exception.ExceptionType.STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StudyValidatorTest {

    private StudyValidator sut = new StudyValidator(new FakeTimeUtil());

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("공백, 빈값 검증")
    void validate_empty_or_whiteSpace(String value) {
        assertThatThrownBy(
            () -> sut.validateEmptyOrWhiteSpace(value, STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE))
            .isInstanceOf(CafegoryException.class)
            .hasMessage(STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaaaaaaaaaaaaaaaa"})
    @DisplayName("이름 길이 초과 검증")
    void fail_validate_name_length(String value) {
        assertThatThrownBy(() -> sut.validateNameLength(value))
            .isInstanceOf(CafegoryException.class)
            .hasMessage(CAFE_STUDY_INVALID_NAME.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaaaaaaaaaaa"})
    @DisplayName("이름 길이 성공 검증")
    void success_validate_name_length(String value) {
        assertDoesNotThrow(() -> sut.validateNameLength(value));
    }
}
