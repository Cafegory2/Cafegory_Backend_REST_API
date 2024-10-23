package com.example.demo.dto.study;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.*;
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

    public static CafeStudyDetailResponse of(CafeStudyEntity cafeStudy, List<CafeStudyCommentEntity> cafeStudyComments) {
        CafeStudyDetailResponse response = new CafeStudyDetailResponse();

        response.commentsInfo = buildCommentTree(cafeStudyComments, response);
        response.cafeStudyInfo = createCafeStudyInfo(cafeStudy);
        response.coordinatorInfo = createCoordinatorInfo(cafeStudy);
        response.cafeInfo = createCafeInfo(cafeStudy);

        return response;
    }

    private static List<Comment> buildCommentTree(List<CafeStudyCommentEntity> cafeStudyComments, CafeStudyDetailResponse response) {
        Map<Long, Comment> commentMap = cafeStudyComments.stream()
            .collect(Collectors.toMap(
                CafeStudyCommentEntity::getId, CafeStudyDetailResponse::toComment));

        List<Comment> rootComments = new ArrayList<>();

        for(CafeStudyCommentEntity comment : cafeStudyComments) {
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

    private static CafeInfo createCafeInfo(CafeStudyEntity cafeStudy) {
        CafeEntity cafe = cafeStudy.getCafe();

        return CafeInfo.builder()
            .id(cafe.getId())
            .imgUrl(cafe.getMainImageUrl())
            .name(cafe.getName())
            .build();
    }

    private static CoordinatorInfo createCoordinatorInfo(CafeStudyEntity cafeStudy) {
        MemberEntity coordinator = cafeStudy.getCoordinator();

        return CoordinatorInfo.builder()
            .id(coordinator.getId())
            .nickname(coordinator.getNickname())
            .build();
    }

    private static CafeStudyInfo createCafeStudyInfo(CafeStudyEntity cafeStudy) {
        return CafeStudyInfo.builder()
            .id(cafeStudy.getId())
            .name(cafeStudy.getName())
            .createdDate(cafeStudy.getCreatedDate())
            .modifiedDate(cafeStudy.getLastModifiedDate())
            .startDateTime(cafeStudy.getStudyPeriod().getStartDateTime())
            .endDateTime(cafeStudy.getStudyPeriod().getEndDateTime())
            .maximumParticipants(cafeStudy.getMaxParticipants())
            .currentParticipants(cafeStudy.getCafeStudyMembers().size())
            .memberComms(cafeStudy.getMemberComms())
            .views(cafeStudy.getViews())
            .introduction(cafeStudy.getIntroduction())
            .tag(cafeStudy.getCafeStudyCafeStudyTags().get(0).getCafeStudyTag().getType())
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
