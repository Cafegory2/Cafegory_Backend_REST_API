package com.example.demo.service.member;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.Role;
import com.example.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long findOrCreateMember(String email, String nickname, String profileImgUrl) {
        return memberRepository.findByEmail(email)
                .map(Member::getId)
                .orElseGet(() -> memberRepository.save(createMember(email, nickname, profileImgUrl)).getId());
    }

    private Member createMember(String email, String nickname, String profileImgUrl) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .profileUrl(profileImgUrl)
                .role(Role.USER)
                .build();
    }
}
