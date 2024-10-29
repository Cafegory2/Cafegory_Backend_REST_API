package com.example.demo.member.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberIdentity {

    private Long id;
    private String nickname;

    public boolean isMember(Long memberId) {
        return this.id.equals(memberId);
    }
}
