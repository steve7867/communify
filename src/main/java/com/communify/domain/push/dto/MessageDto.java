package com.communify.domain.push.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MessageDto {

    public static final String POST_UPLOAD_MESSAGE_TITLE_FORMAT = "%s님이 새로운 게시글을 작성했습니다.";
    public static final String LIKE_MESSAGE_TITLE = "회원님의 게시글에 '좋아요'가 눌렸습니다.";
    public static final String COMMENT_MESSAGE_TITLE = "회원님의 게시글에 댓글이 추가되었습니다.";
    public static final String FOLLOW_MESSAGE_TITLE_FORMAT = "%s님이 회원님을 팔로우했습니다.";

    private final String title;
    private final String body;
    private final String token;

    public static MessageDto forPostUpload(String token, String writerName) {
        return MessageDto.builder()
                .title(String.format(POST_UPLOAD_MESSAGE_TITLE_FORMAT, writerName))
                .token(token)
                .build();
    }

    public static MessageDto forPostLike(String token) {
        return MessageDto.builder()
                .title(LIKE_MESSAGE_TITLE)
                .token(token)
                .build();
    }

    public static MessageDto forComment(String token) {
        return MessageDto.builder()
                .title(COMMENT_MESSAGE_TITLE)
                .token(token)
                .build();
    }

    public static MessageDto forFollow(String token, String followerName) {
        return MessageDto.builder()
                .title(String.format(FOLLOW_MESSAGE_TITLE_FORMAT, followerName))
                .token(token)
                .build();
    }
}
