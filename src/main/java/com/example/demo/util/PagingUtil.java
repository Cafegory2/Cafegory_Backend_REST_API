package com.example.demo.util;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class PagingUtil {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 30;

    public static org.springframework.data.domain.Pageable createByDefault() {
        return org.springframework.data.domain.PageRequest.of(DEFAULT_PAGE - 1, DEFAULT_SIZE);
    }

    public static org.springframework.data.domain.Pageable of(int page, int size) {
        int validatedPage = (page <= 0) ? 1 : page;
        int validatedSize = (size > MAX_SIZE ? DEFAULT_SIZE : size);
        return org.springframework.data.domain.PageRequest.of(validatedPage - 1, validatedSize);
    }

    public static <T> Slice<T> toSlice(List<T> contents, Pageable pageable) {
        boolean hasNext = contents.size() > pageable.getPageSize();
        if (hasNext) {
            contents.remove(contents.size() - 1);
        }

        return new SliceImpl<>(contents, pageable, hasNext);
    }

    public static int limitToSlice(Pageable pageable) {
        return pageable.getPageSize() + 1;
    }

    public static <T> Slice<T> toSlice(JPAQuery<T> query, Pageable pageable) {
        List<T> contents = query
            .offset(pageable.getOffset())
            .limit(limitToSlice(pageable))
            .fetch();

        return toSlice(contents, pageable);
    }
}
