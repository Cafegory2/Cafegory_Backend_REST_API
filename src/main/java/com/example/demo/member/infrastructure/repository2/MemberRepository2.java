package com.example.demo.member.infrastructure.repository2;

import com.example.demo.member.domain.MemberContent;
import com.example.demo.member.domain.MemberId;

public interface MemberRepository2 {

    void update(MemberContent content, MemberId memberId);
}
