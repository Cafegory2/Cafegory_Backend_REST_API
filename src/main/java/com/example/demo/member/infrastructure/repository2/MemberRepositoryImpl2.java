package com.example.demo.member.infrastructure.repository2;

import com.example.demo.member.domain.MemberContent;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl2 implements MemberRepository2 {

    private final MemberRepository memberJpaRepository;

    @Override
    public void update(MemberContent content, MemberId memberId) {
        MemberEntity memberEntity = memberJpaRepository.findById(memberId.getId())
                .orElseThrow(() -> new IllegalArgumentException("member가 존재하지 않습니다."));

        memberEntity.setNickname(content.getNickname());
        memberEntity.setProfileUrl(content.getImgUrl());
    }
}
