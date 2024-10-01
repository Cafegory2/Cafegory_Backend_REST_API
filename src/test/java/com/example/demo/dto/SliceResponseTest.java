package com.example.demo.dto;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SliceResponseTest {

    @Test
    void map() {
        //given
        List<TestDto1> content = List.of(new TestDto1(1L, "테스트1"), new TestDto1(2L, "테스트2"), new TestDto1(3L, "테스트3"));
        SliceResponse<TestDto1> testDto1Slice = SliceResponse.of(new SliceImpl<>(content, PageRequest.of(1, 2), true));
        //when
        SliceResponse<TestDto2> testDto2Slice = testDto1Slice.map(TestDto2::of);
        //then
        List<TestDto2> result = testDto2Slice.getContent();

        assertThat(result)
            .extracting("id2")
            .containsExactly(1L, 2L, 3L);
    }

    @Getter
    private static class TestDto1 {

        private final Long id1;
        private final String name1;

        private TestDto1(Long id1, String name1) {
            this.id1 = id1;
            this.name1 = name1;
        }
    }

    private static class TestDto2 {

        private final Long id2;
        private final String name2;

        private TestDto2(Long id2, String name2) {
            this.id2 = id2;
            this.name2 = name2;
        }

        public static TestDto2 of(TestDto1 testDto1) {
            return new TestDto2(testDto1.getId1(), testDto1.getName1());
        }
    }
}