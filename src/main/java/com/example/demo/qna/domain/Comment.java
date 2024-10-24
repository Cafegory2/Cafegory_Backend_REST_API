package com.example.demo.qna.domain;

import com.example.demo.domain.DefaultDate;
import com.example.demo.member.domain.MemberIdentity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Comment {

    private Long commentId;
    private Long parentCommentId;
    private Long cafeStudyId;
    private MemberIdentity member;
    private String content;
    private DefaultDate date;
}
