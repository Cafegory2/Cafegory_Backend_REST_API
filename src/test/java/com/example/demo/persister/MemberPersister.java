package com.example.demo.persister;

import com.example.demo.member.domain.BeverageSize;
import com.example.demo.member.domain.Role;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;

public class MemberPersister {

    private Role role = Role.USER;
    private String nickname  = "테스트 멤버 닉네임";
    private String email = "test@test.com";
    private String profileUrl = "https://testprofile.com/testimages";
    private String bio = "테스트 자기소개";
    private int participationCount = 0;
    private BeverageSize beverageSize = BeverageSize.SHORT;

    private MemberPersister() {}

    private MemberPersister(MemberPersister copy) {
        this.role = copy.role;
        this.nickname = copy.nickname;
        this.email = copy.email;
        this.profileUrl = copy.profileUrl;
        this.bio = copy.bio;
        this.participationCount = copy.participationCount;
        this.beverageSize = copy.beverageSize;
    }

    public MemberPersister but() {
        return new MemberPersister(this);
    }

    public static MemberPersister aMember() {
        return new MemberPersister();
    }

    public MemberPersister withRole(Role role) {
        this.role = role;
        return this;
    }

    public MemberPersister withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public MemberPersister withEmail(String email) {
        this.email = email;
        return this;
    }

    public MemberPersister asCoordinator() {
        this.email = "coordinator@test.com";
        return this;
    }

    public MemberPersister asParticipant() {
        this.email = "participant@test.com";
        return this;
    }

    public MemberPersister asParticipant(int sequence) {
        this.email = "participant" + sequence + "@test.com";
        return this;
    }

    public MemberPersister withProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }

    public MemberPersister withBio(String bio) {
        this.bio = bio;
        return this;
    }

    public MemberPersister withParticipationCount(int participationCount) {
        this.participationCount = participationCount;
        return this;
    }

    public MemberPersister withBeverageSize(BeverageSize beverageSize) {
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

    public static class MemberSaver {
        private static MemberRepository memberRepository;

        public static void init(MemberRepository memberRepo) {
            memberRepository = memberRepo;
        }
    }

    public MemberEntity save() {
        return MemberSaver.memberRepository.save(build());
    }
}
