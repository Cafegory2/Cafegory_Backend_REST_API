package com.example.demo.mapper;

import com.example.demo.dto.profile.WelcomeProfileResponse;
import com.example.demo.implement.member.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public WelcomeProfileResponse toWelcomeProfileResponse(MemberEntity member) {
        return new WelcomeProfileResponse(member.getNickname(), member.getProfileUrl());
    }
}
