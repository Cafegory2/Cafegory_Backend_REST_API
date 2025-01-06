package com.example.demo.builder;

import com.example.demo.domain.member.domain.MemberIdentity;

public class MemberIdentityBuilder {

    private Long id = 1L;
    private String nickname = "테스트 닉네임";

    private MemberIdentityBuilder() {}

    private MemberIdentityBuilder(MemberIdentityBuilder copy) {
        this.id = copy.id;
        this.nickname = copy.nickname;
    }

    public MemberIdentityBuilder but() {
        return new MemberIdentityBuilder(this);
    }

    public static MemberIdentityBuilder aMemberIdentity() {
        return new MemberIdentityBuilder();
    }

    public MemberIdentityBuilder withMemberId(Long memberId) {
        this.id = memberId;
        return this;
    }

    public MemberIdentityBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public MemberIdentity build() {
        return MemberIdentity.builder()
                .id(this.id)
                .nickname(this.nickname)
                .build();
    }
}
