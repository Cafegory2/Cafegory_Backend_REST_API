package com.example.demo.study.infrastructure.repository2;

import com.example.demo.study.infrastructure.CafeStudyMemberEntity;

import java.util.List;

public interface StudyMemberQueryRepository2 {

    List<StudyMember> findByMember_Id(Long memberId);
}
