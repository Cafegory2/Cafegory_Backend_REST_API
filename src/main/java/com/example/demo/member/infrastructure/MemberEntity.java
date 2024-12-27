package com.example.demo.member.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.member.domain.*;
import org.hibernate.annotations.Where;

import com.example.demo.domain.DateAudit;
import com.example.demo.trash.implement.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Where(clause = "deleted_date IS NULL")
@Table(name = "member")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String nickname;

    @Column(unique = true)
    private String email;

    private String profileUrl;
    private String bio;
    private int participationCount;

    @Enumerated(EnumType.STRING)
    private BeverageSize beverageSize;

    private String refreshToken;

    public void changeProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public MemberEntity(Long id) {
        this.id = id;
    }

    @Builder
    private MemberEntity(Role role, String nickname, String email, String profileUrl, String bio,
                         int participationCount, BeverageSize beverageSize, String refreshToken) {
        this.role = role;
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
        this.bio = bio;
        this.participationCount = participationCount;
        this.beverageSize = beverageSize;
        this.refreshToken = refreshToken;
    }

    public Member toMember() {
        return Member.builder()
                .id(this.id)
                .content(
                        MemberContent.builder()
                                .nickname(this.nickname)
                                .imgUrl(this.profileUrl)
                                .build()
                )
                .role(this.role)
                .email(this.email)
                .bio(this.bio)
                .beverageSize(this.beverageSize)
                .dateAudit(
                        DateAudit.builder()
                                .createdDate(this.getCreatedDate())
                                .modifiedDate(this.getLastModifiedDate())
                                .build()
                )
                .refreshToken(this.refreshToken)
                .build();
    }
}
