package com.example.demo.mapper;

import com.example.demo.dto.profile.WelcomeProfileResponse;
import com.example.demo.implement.member.Member;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public WelcomeProfileResponse toWelcomeProfileResponse(Member member) {
        return new WelcomeProfileResponse(member.getNickname(), member.getProfileUrl());
    }
}
