package com.example.demo.service.member;

import com.example.demo.config.ServiceTest;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.implement.member.Member;
import com.example.demo.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest extends ServiceTest {

    @Autowired
    private MemberService sut;
    @Autowired
    private MemberSaveHelper memberSaveHelper;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원이 존재하면 회원을 찾는다.")
    void find_member_if_exists() {
        //given
        Member member = memberSaveHelper.saveMember();
        //when
        Long memberId = sut.findOrCreateMember(member.getEmail(), member.getNickname(), member.getProfileUrl());
        //then
        List<Member> members = memberRepository.findAll();
        assertAll(
                () -> assertThat(memberId).isEqualTo(member.getId()),
                () -> assertThat(members.size()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("회원이 존재하지 않으면 회원을 생성한다.")
    void create_member_if_not_exists() {
        //when
        sut.findOrCreateMember("test@gmail.com", "testNickname", "testProfileUrl");
        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(1);
    }

}
