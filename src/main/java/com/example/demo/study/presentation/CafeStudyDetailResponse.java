package com.example.demo.study.presentation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.study.domain.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeStudyDetailResponse {

    private CafeStudyInfo cafeStudyInfo;
    private CoordinatorInfo coordinatorInfo;
    private CafeInfo cafeInfo;

    public static CafeStudyDetailResponse of(
        Cafe cafe, Study study, ViewCount viewCount, ParticipantCount participantCount
    ) {
        CafeStudyDetailResponse response = new CafeStudyDetailResponse();

        response.cafeStudyInfo = createCafeStudyInfo(study, viewCount, participantCount);
        response.coordinatorInfo = createCoordinatorInfo(study);
        response.cafeInfo = createCafeInfo(cafe);

        return response;
    }

    private static List<Comment> buildCommentTree(List<CafeStudyCommentEntity> cafeStudyComments,
                                                  CafeStudyDetailResponse response) {
        Map<Long, Comment> commentMap = cafeStudyComments.stream()
            .collect(Collectors.toMap(
                CafeStudyCommentEntity::getId, CafeStudyDetailResponse::toComment));

        List<Comment> rootComments = new ArrayList<>();

        for (CafeStudyCommentEntity comment : cafeStudyComments) {
            if (!comment.hasParentComment()) {
                rootComments.add(commentMap.get(comment.getId()));
            } else {
                Comment parentComment = commentMap.get(comment.getParentComment().getId());

                if (parentComment != null) {
                    parentComment.getReplies().add(commentMap.get(comment.getId()));
                }
            }
        }

        return rootComments;
    }

    private static CafeInfo createCafeInfo(Cafe cafe) {
        return CafeInfo.builder()
            .id(cafe.getId())
            .imgUrl(cafe.getImgUrl())
            .name(cafe.getName())
            .build();
    }

    private static CoordinatorInfo createCoordinatorInfo(Study study) {
        Coordinator coordinator = study.getCoordinator();

        return CoordinatorInfo.builder()
            .id(coordinator.getId())
            .nickname(coordinator.getNickname())
            .build();
    }

    private static CafeStudyInfo createCafeStudyInfo(
        Study study, ViewCount viewCount, ParticipantCount participantCount
    ) {
        return CafeStudyInfo.builder()
            .id(study.getId())
            .name(study.getName())
            .createdDate(study.getDateAudit().getCreatedDate())
            .modifiedDate(study.getDateAudit().getModifiedDate())
            .startDateTime(study.getSchedule().getStartDateTime())
            .endDateTime(study.getSchedule().getEndDateTime())
            .maximumParticipants(study.getMaxParticipantCount())
            .currentParticipants(participantCount.getCurrentCount())
            .memberComms(study.getMemberComms())
            .views(viewCount.getTotalViews())
            .introduction(study.getIntroduction())
            .tag(study.getTags())
            .build();
    }

    private static Comment toComment(CafeStudyCommentEntity cafeStudyComment) {
        return Comment.builder()
            .writerInfo(
                Comment.WriterInfo.builder()
                    .id(cafeStudyComment.getId())
                    .nickname(cafeStudyComment.getAuthor().getNickname())
                    .profileUrl(cafeStudyComment.getAuthor().getProfileUrl())
                    .build()
            )
            .commentInfo(
                Comment.CommentInfo.builder()
                    .id(cafeStudyComment.getId())
                    .content(cafeStudyComment.getContent())
                    .createdDate(cafeStudyComment.getCreatedDate())
                    .build()
            )
            .replies(new ArrayList<>())
            .build();
    }

    @Getter
    @Setter
    @Builder
    private static class CafeStudyInfo {

        private Long id;
        private String name;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private int maximumParticipants;
        private int currentParticipants;
        private MemberComms memberComms;
        private int views;
        private String introduction;
        private List<CafeStudyTagType> tag;
    }

    @Getter
    @Setter
    @Builder
    private static class CoordinatorInfo {

        private Long id;
        private String nickname;
    }

    @Getter
    @Setter
    @Builder
    private static class CafeInfo {

        private Long id;
        private String imgUrl;
        private String name;
    }

    @Getter
    @Setter
    @Builder
    private static class Comment {

        private WriterInfo writerInfo;
        private CommentInfo commentInfo;
        private List<Comment> replies;

        @Getter
        @Setter
        @Builder
        private static class WriterInfo {

            private Long id;
            private String nickname;
            private String profileUrl;
        }

        @Getter
        @Setter
        @Builder
        private static class CommentInfo {

            private Long id;
            private String content;
            private LocalDateTime createdDate;
        }
    }
}
