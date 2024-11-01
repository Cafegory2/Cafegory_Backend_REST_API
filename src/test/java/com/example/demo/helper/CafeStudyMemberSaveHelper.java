package com.example.demo.helper;

import com.example.demo.factory.TestCafeStudyMemberFactory;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyMemberEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class CafeStudyMemberSaveHelper {

    private final StudyMemberRepository studyMemberRepository;
    private final CafeStudyRepository cafeStudyRepository;
    private final MemberRepository memberRepository;

    public CafeStudyMemberEntity saveCafeStudyMember(CafeStudyEntity cafeStudy, MemberEntity member) {
        CafeStudyEntity mergedCafeStudy = cafeStudyRepository.save(cafeStudy);
        MemberEntity mergedMember = memberRepository.save(member);

        CafeStudyMemberEntity studyMember =
            TestCafeStudyMemberFactory.createStudyMember(mergedCafeStudy, mergedMember);
        return studyMemberRepository.save(studyMember);
    }
}
