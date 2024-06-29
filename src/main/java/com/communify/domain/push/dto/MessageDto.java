package com.communify.domain.push.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {

    public static final String POST_UPLOAD_MESSAGE_TITLE_FORMAT = "%s님이 새로운 게시글을 작성했습니다.";
    public static final String LIKE_MESSAGE_TITLE_FORMAT = "%s님이 회원님의 게시글에 '좋아요'를 눌렀습니다.";
    public static final String COMMENT_MESSAGE_TITLE_FORMAT = "%s님이 회원님의 게시글에 댓글을 작성하였습니다.";
    public static final String FOLLOW_MESSAGE_TITLE_FORMAT = "%s님이 회원님을 팔로우했습니다.";

    private final String title;
    private final String body;

    public static MessageDto forPostUpload(final String writerName) {
        return MessageDto.builder()
                .title(String.format(POST_UPLOAD_MESSAGE_TITLE_FORMAT, writerName))
                .build();
    }

    public static MessageDto forPostLike(final String likerName) {
        return MessageDto.builder()
                .title(String.format(LIKE_MESSAGE_TITLE_FORMAT, likerName))
                .build();
    }

    public static MessageDto forComment(final String commentWriterName, final String commentContent) {
        return MessageDto.builder()
                .title(String.format(COMMENT_MESSAGE_TITLE_FORMAT, commentWriterName))
                .body(commentContent)
                .build();
    }

    public static MessageDto forFollow(final String followerName) {
        return MessageDto.builder()
                .title(String.format(FOLLOW_MESSAGE_TITLE_FORMAT, followerName))
                .build();
    }
}
