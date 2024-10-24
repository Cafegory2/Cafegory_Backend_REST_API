package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DefaultDate {

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
