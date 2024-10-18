package com.example.demo.packageex.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class MemberIdentity {

    private Long id;
    private String nickname;
}
