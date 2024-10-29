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
    //TODO member 이름 수정
    private MemberIdentity member;
    private String content;
    private DefaultDate date;
}
