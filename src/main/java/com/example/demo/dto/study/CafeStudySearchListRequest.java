package com.example.demo.dto.study;

import com.example.demo.dto.PagedRequest;
import com.example.demo.implement.study.CafeStudyTagType;
import com.example.demo.implement.study.CafeTagType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CafeStudySearchListRequest extends PagedRequest {

//    @NotBlank
    private String keyword;
    private LocalDate date;
    private CafeStudyTagType cafeStudyTagType;
    private List<CafeTagType> cafeTagTypes = new ArrayList<>();

    protected CafeStudySearchListRequest() {
        super();
    }

    @Builder
    private CafeStudySearchListRequest(int page, int sizePerPage, String keyword, LocalDate date, CafeStudyTagType cafeStudyTagType, List<CafeTagType> cafeTagTypes) {
        super(page, sizePerPage);
        this.keyword = keyword;
        this.date = date;
        this.cafeStudyTagType = cafeStudyTagType;
        this.cafeTagTypes = cafeTagTypes;
    }
}
