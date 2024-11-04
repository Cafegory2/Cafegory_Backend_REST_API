package com.example.demo.qna.domain;

import com.example.demo.domain.DateAudit;
import com.example.demo.member.domain.MemberIdentity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Comment {

    private Long commentId;
    private Long parentCommentId;
    private Long cafeStudyId;
    private MemberIdentity author;
    private String content;
    private DateAudit date;

    public boolean isAuthor(Long memberId) {
        return this.author.isMember(memberId);
    }
}