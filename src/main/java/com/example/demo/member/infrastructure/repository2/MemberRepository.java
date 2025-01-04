package com.example.demo.member.infrastructure.repository2;

import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberContent;
import com.example.demo.member.domain.MemberId;

public interface MemberRepository {

	MemberId save(Member content);

	void update(MemberContent content, MemberId memberId);

	void updateRefreshToken(MemberId memberId, String refreshToken);
}
