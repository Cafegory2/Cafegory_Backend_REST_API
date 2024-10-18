package com.example.demo.dto.study;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.*;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.packageex.studyqna.repository.CafeStudyCommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeStudyDetailResponse {

    private CafeStudyInfo cafeStudyInfo;
    private CoordinatorInfo coordinatorInfo;
    private CafeInfo cafeInfo;
    private List<Comment> commentsInfo = new ArrayList<>();

    public static CafeStudyDetailResponse of(CafeStudyEntity cafeStudyEntity, List<CafeStudyCommentEntity> cafeStudyCommentEntities) {
        CafeStudyDetailResponse response = new CafeStudyDetailResponse();

        response.commentsInfo = buildCommentTree(cafeStudyCommentEntities, response);
        response.cafeStudyInfo = createCafeStudyInfo(cafeStudyEntity);
        response.coordinatorInfo = createCoordinatorInfo(cafeStudyEntity);
        response.cafeInfo = createCafeInfo(cafeStudyEntity);

        return response;
    }

    private static List<Comment> buildCommentTree(List<CafeStudyCommentEntity> cafeStudyCommentEntities, CafeStudyDetailResponse response) {
        Map<Long, Comment> commentMap = cafeStudyCommentEntities.stream()
            .collect(Collectors.toMap(
                CafeStudyCommentEntity::getId, CafeStudyDetailResponse::toComment));

        List<Comment> rootComments = new ArrayList<>();

        for(CafeStudyCommentEntity comment : cafeStudyCommentEntities) {
            if(!comment.hasParentComment()) {
                rootComments.add(commentMap.get(comment.getId()));
            } else {
                Comment parentComment = commentMap.get(comment.getParentComment().getId());

                if(parentComment != null) {
                    parentComment.getReplies().add(commentMap.get(comment.getId()));
                }
            }
        }

        return rootComments;
    }

    private static CafeInfo createCafeInfo(CafeStudyEntity cafeStudyEntity) {
        Cafe cafe = cafeStudyEntity.getCafe();

        return CafeInfo.builder()
            .id(cafe.getId())
            .imgUrl(cafe.getMainImageUrl())
            .name(cafe.getName())
            .build();
    }

    private static CoordinatorInfo createCoordinatorInfo(CafeStudyEntity cafeStudyEntity) {
        Member coordinator = cafeStudyEntity.getCoordinator();

        return CoordinatorInfo.builder()
            .id(coordinator.getId())
            .nickname(coordinator.getNickname())
            .build();
    }

    private static CafeStudyInfo createCafeStudyInfo(CafeStudyEntity cafeStudyEntity) {
        return CafeStudyInfo.builder()
            .id(cafeStudyEntity.getId())
            .name(cafeStudyEntity.getName())
            .createdDate(cafeStudyEntity.getCreatedDate())
            .modifiedDate(cafeStudyEntity.getLastModifiedDate())
            .startDateTime(cafeStudyEntity.getStudyPeriod().getStartDateTime())
            .endDateTime(cafeStudyEntity.getStudyPeriod().getEndDateTime())
            .maximumParticipants(cafeStudyEntity.getMaxParticipants())
            .currentParticipants(cafeStudyEntity.getCafeStudyMembers().size())
            .memberComms(cafeStudyEntity.getMemberComms())
            .views(cafeStudyEntity.getViews())
            .introduction(cafeStudyEntity.getIntroduction())
            .tag(cafeStudyEntity.getCafeStudyCafeStudyTags().get(0).getCafeStudyTag().getType())
            .build();
    }

    private static Comment toComment(CafeStudyCommentEntity cafeStudyCommentEntity) {
        return Comment.builder()
            .writerInfo(
                Comment.WriterInfo.builder()
                    .id(cafeStudyCommentEntity.getId())
                    .nickname(cafeStudyCommentEntity.getAuthor().getNickname())
                    .profileUrl(cafeStudyCommentEntity.getAuthor().getProfileUrl())
                    .build()
            )
            .commentInfo(
                Comment.CommentInfo.builder()
                    .id(cafeStudyCommentEntity.getId())
                    .content(cafeStudyCommentEntity.getContent())
                    .createdDate(cafeStudyCommentEntity.getCreatedDate())
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
        private CafeStudyTagType tag;
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
