package com.example.demo.testbuilder;

import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.repository.cafe.BusinessHourRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static com.example.demo.testbuilder.CafeBuilder.*;

public class BusinessHourBuilder {

    private DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
    private LocalTime openingTime = LocalTime.of(9, 0);
    private LocalTime closingTime = LocalTime.of(21, 0);
    private CafeEntity cafe = aCafe().build();

    private BusinessHourBuilder() {}

    private BusinessHourBuilder(BusinessHourBuilder copy) {
        this.dayOfWeek = copy.dayOfWeek;
        this.openingTime = copy.openingTime;
        this.closingTime = copy.closingTime;
        this.cafe = copy.cafe;
    }

    public BusinessHourBuilder but() {
        return new BusinessHourBuilder(this);
    }

    public static BusinessHourBuilder aBusinessHour() {
        return new BusinessHourBuilder();
    }

    public BusinessHourBuilder withDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public BusinessHourBuilder withOpeningTime(int hour, int minute) {
        this.openingTime = LocalTime.of(hour, minute);
        return this;
    }

    public BusinessHourBuilder withClosingTime(int hour, int minute) {
        this.closingTime = LocalTime.of(hour, minute);
        return this;
    }

    public BusinessHourBuilder withClosingEndOfDay() {
        this.closingTime = LocalTime.of(23, 59, 59);
        return this;
    }

    public BusinessHourBuilder with(CafeEntity cafe) {
        this.cafe = cafe;
        return this;
    }

    public BusinessHourEntity build() {
        return BusinessHourEntity.builder()
            .dayOfWeek(this.dayOfWeek)
            .openingTime(this.openingTime)
            .closingTime(this.closingTime)
            .cafe(this.cafe)
            .build();
    }

    public static class BusinessHourSaver {
        private static BusinessHourRepository businessHourRepository;

        public static void init(BusinessHourRepository businessHOurRepo) {
            businessHourRepository = businessHOurRepo;
        }
    }

    public BusinessHourEntity save() {
        return BusinessHourSaver.businessHourRepository.save(build());
    }
}
