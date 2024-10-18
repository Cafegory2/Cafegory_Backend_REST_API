package com.example.demo.packageex.studyqna.domain;

import com.example.demo.packageex.member.domain.MemberIdentity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeStudyComment {

    private Long cafeStudyCommentId;
    private Long parentCommentId;
    private Long cafeStudyId;
    private MemberIdentity member;
    private String content;
}
