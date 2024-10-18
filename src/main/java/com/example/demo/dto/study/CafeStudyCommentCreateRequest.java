package com.example.demo.dto.study;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.implement.member.Member;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.packageex.studyqna.repository.CafeStudyCommentEntity;
import com.example.demo.implement.study.StudyMemberRole;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeStudyCommentCreateRequest {

	@NotNull
	private Long cafeStudyId;
	private Long parentCommentId;
	@NotBlank
	private String content;

	@Builder
	private CafeStudyCommentCreateRequest(Long cafeStudyId, Long parentCommentId, String content) {
		this.cafeStudyId = cafeStudyId;
		this.parentCommentId = parentCommentId;
		this.content = content;
	}

	//TODO 이부분 좀 이상한것 같음.
	public CafeStudyCommentEntity toCafeStudyComment(
		Member member, StudyMemberRole studyMemberRole, @Nullable CafeStudyCommentEntity parentComment, CafeStudyEntity cafeStudyEntity) {
		return CafeStudyCommentEntity.builder()
			.author(member)
			.studyMemberRole(studyMemberRole)
			.content(this.content)
			.parentComment(parentComment)
			.cafeStudyEntity(cafeStudyEntity)
			.build();
	}
}
