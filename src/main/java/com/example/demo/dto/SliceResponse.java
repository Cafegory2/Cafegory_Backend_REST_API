package com.example.demo.dto;

import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class SliceResponse<T> {

    private final boolean hasNext;
    private final Integer nextPage;
    private final List<T> content;

    private SliceResponse(boolean hasNext, Integer nextPage, List<T> content) {
        this.hasNext = hasNext;
        this.nextPage = hasNext ? nextPage : null;
        this.content = content;
    }

    public static <T> SliceResponse<T> of(Slice<T> content) {
        return new SliceResponse<>(content.hasNext(), content.getNumber() + 1, content.getContent());
    }

    public <U> SliceResponse<U> map(Function<T, U> converter) {
        List<U> convertedContent = this.content.stream()
            .map(converter)
            .collect(Collectors.toList());

        return new SliceResponse<>(this.hasNext, this.nextPage, convertedContent);
    }
}
