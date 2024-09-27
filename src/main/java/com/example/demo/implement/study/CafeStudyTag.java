package com.example.demo.implement.study;

import com.example.demo.implement.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cafe_study_tag")
public class CafeStudyTag extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cafe_study_tag_id")
    private Long id;

    private String name;
}
