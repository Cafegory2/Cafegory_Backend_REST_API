package com.example.demo.builder;

import com.example.demo.domain.DateAudit;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateAuditBuilder {

    private LocalDateTime createdDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    private LocalDateTime modifiedDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    private DateAuditBuilder() {}

    private DateAuditBuilder(DateAuditBuilder copy) {
        this.createdDate = copy.createdDate;
        this.modifiedDate = copy.modifiedDate;
    }

    public DateAuditBuilder but() {
        return new DateAuditBuilder(this);
    }

    public static DateAuditBuilder aDateAudit() {
        return new DateAuditBuilder();
    }

    public DateAuditBuilder withCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public DateAuditBuilder withModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public DateAudit build() {
        return DateAudit.builder()
                .createdDate(this.createdDate)
                .modifiedDate(this.modifiedDate)
                .build();
    }
}
