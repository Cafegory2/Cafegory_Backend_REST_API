package com.example.demo.dto;

import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class SliceResponse<T> {

    private final boolean hasNext;
    private final boolean hasPrevious;
    private final int currentPage;
    private final int currentContentSize;
    private final List<T> content;

    private SliceResponse(boolean hasNext, boolean hasPrevious, int currentPage, int currentContentSize, List<T> content) {
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.currentPage = currentPage;
        this.currentContentSize = currentContentSize;
        this.content = content;
    }

    public static <T> SliceResponse<T> of(Slice<T> content) {
        return new SliceResponse<>(content.hasNext(), content.hasPrevious(), content.getNumber(), content.getNumberOfElements(), content.getContent());
    }

    public <U> SliceResponse<U> map(Function<T, U> converter) {
        List<U> convertedContent = this.content.stream()
            .map(converter)
            .collect(Collectors.toList());

        return new SliceResponse<>(this.hasNext, this.hasPrevious, this.currentPage, this.currentContentSize, convertedContent);
    }
}
