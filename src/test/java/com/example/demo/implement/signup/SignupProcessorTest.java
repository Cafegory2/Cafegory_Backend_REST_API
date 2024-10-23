package com.example.demo.implement.signup;

import com.example.demo.config.ServiceTest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SignupProcessorTest extends ServiceTest {

    @Autowired
    private SignupProcessor sut;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberSaveHelper memberSaveHelper;

    @Test
    @DisplayName("회원가입을 한다.")
    void signup() {
        //when
        sut.signup("new@gmail.com", "newUser");
        //then
        List<MemberEntity> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("이미 등록된 이메일로는 회원가입을 할 수 없다.")
    void email_already_registered_prevents_signup() {
        //given
        memberSaveHelper.saveMember("new@gmail.com");
        //then
        assertThatThrownBy(() -> sut.signup("new@gmail.com", "newUser"))
            .isInstanceOf(CafegoryException.class)
            .hasMessage(ExceptionType.MEMBER_ALREADY_EXISTS.getErrorMessage());
    }
}
