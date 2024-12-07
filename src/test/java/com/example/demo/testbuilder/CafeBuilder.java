package com.example.demo.testbuilder;

import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.factory.TestBusinessHourFactory;
import com.example.demo.implement.cafe.Address;
import com.example.demo.repository.cafe.BusinessHourRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class CafeBuilder {

    private String name = "테스트 카페 이름";
    private String mainImageUrl = "https://testimageurl.com/testimages";
    private Address address = new Address("서울 테스트구 테스트로1길 1 1층", "테스트동");
    private String sns = "https://www.testsns.com/testsns";

    private CafeBuilder() {}

    private CafeBuilder(CafeBuilder copy) {
        this.name = copy.name;
        this.mainImageUrl = copy.mainImageUrl;
        this.address = copy.address;
        this.sns = copy.sns;
    }

    public CafeBuilder but() {
        return new CafeBuilder(this);
    }

    public static CafeBuilder aCafe() {
        return new CafeBuilder();
    }

    public CafeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CafeBuilder withMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
        return this;
    }

    public CafeBuilder withAddress(String fullAddress, String region) {
        this.address = new Address(fullAddress, region);
        return this;
    }

    public CafeBuilder withSns(String sns) {
        this.sns = sns;
        return this;
    }

    public CafeEntity build() {
        return CafeEntity.builder()
            .name(this.name)
            .mainImageUrl(this.mainImageUrl)
            .address(this.address)
            .sns(this.sns)
            .build();
    }

    public CafeEntity saveWith7daysFrom9To21() {
        return BusinessHourSaver.saveWith7daysFrom9To21(save());
    }

    public CafeEntity saveWith24For7() {
        return BusinessHourSaver.saveWith24For7(save());
    }

    public CafeEntity save() {
        return CafeSaver.cafeRepository.save(build());
    }

    public static class CafeSaver {
        private static CafeRepository cafeRepository;

        public static void init(CafeRepository cafeRepo) {
            cafeRepository = cafeRepo;
        }
    }

    public static class BusinessHourSaver {
        static BusinessHourRepository businessHourRepository;

        public static void init(BusinessHourRepository businessHourRepo) {
            businessHourRepository = businessHourRepo;
        }

        static CafeEntity saveWith7daysFrom9To21(CafeEntity cafeEntity) {
            saveBusinessHoursWith7daysFrom9To21(cafeEntity);
            return cafeEntity;
        }

        static CafeEntity saveWith24For7(CafeEntity cafeEntity) {
            saveBusinessHourWith24For7(cafeEntity);
            return cafeEntity;
        }

        private static void saveBusinessHoursWith7daysFrom9To21(CafeEntity cafe) {
            for (DayOfWeek day : DayOfWeek.values()) {
                BusinessHourEntity businessHour = buildBusinessHourWithDayAndTime(cafe, day,
                    LocalTime.of(9, 0), LocalTime.of(21, 0));
                businessHourRepository.save(businessHour);
            }
        }

        private static void saveBusinessHourWith24For7(CafeEntity cafe) {
            for (DayOfWeek day : DayOfWeek.values()) {
                BusinessHourEntity businessHour = TestBusinessHourFactory.createBusinessHourWithDayAnd24For7(cafe, day);
                businessHourRepository.save(businessHour);
            }
        }

        private static BusinessHourEntity buildBusinessHourWithDayAndTime(
            CafeEntity cafe, DayOfWeek day, LocalTime openingTime, LocalTime closingTime) {
            return BusinessHourEntity.builder()
                .dayOfWeek(day)
                .openingTime(openingTime)
                .closingTime(closingTime)
                .cafe(cafe)
                .build();
        }
    }
}
