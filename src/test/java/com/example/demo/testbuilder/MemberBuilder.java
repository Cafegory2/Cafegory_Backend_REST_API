package com.example.demo.testbuilder;

import com.example.demo.implement.member.BeverageSize;
import com.example.demo.implement.member.Role;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.repository.member.MemberRepository;
import lombok.Setter;

public class MemberBuilder {

    private Role role = Role.USER;
    private String nickname  = "테스트 멤버 닉네임";
    private String email = "test@test.com";
    private String profileUrl = "https://testprofile.com/testimages";
    private String bio = "테스트 자기소개";
    private int participationCount = 0;
    private BeverageSize beverageSize = BeverageSize.SHORT;

    private MemberBuilder() {}

    private MemberBuilder(MemberBuilder copy) {
        this.role = copy.role;
        this.nickname = copy.nickname;
        this.email = copy.email;
        this.profileUrl = copy.profileUrl;
        this.bio = copy.bio;
        this.participationCount = copy.participationCount;
        this.beverageSize = copy.beverageSize;
    }

    private MemberBuilder but() {
        return new MemberBuilder(this);
    }

    public static MemberBuilder aMember() {
        return new MemberBuilder();
    }

    public MemberBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public MemberBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public MemberBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public MemberBuilder withProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }

    public MemberBuilder withBio(String bio) {
        this.bio = bio;
        return this;
    }

    public MemberBuilder withParticipationCount(int participationCount) {
        this.participationCount = participationCount;
        return this;
    }

    public MemberBuilder withBeverageSize(BeverageSize beverageSize) {
        this.beverageSize = beverageSize;
        return this;
    }

    public MemberEntity build() {
        return MemberEntity.builder()
            .role(this.role)
            .nickname(this.nickname)
            .email(this.email)
            .profileUrl(this.profileUrl)
            .bio(this.bio)
            .participationCount(this.participationCount)
            .beverageSize(this.beverageSize)
            .build();
    }

    public MemberEntity save() {
        return MemberSaver.memberRepository.save(build());
    }

    public static class MemberSaver {
        static MemberRepository memberRepository;

        public static void init(MemberRepository memberRepo) {
            memberRepository = memberRepo;
        }
    }
}
