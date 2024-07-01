package com.communify.domain.push.dao;

import com.communify.domain.comment.dto.CommentUploadRequest;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.domain.post.dto.PostUploadRequest;
import com.communify.domain.push.dto.InfoForNotification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PushRepository {

    List<InfoForNotification> findInfoForPostUploadNotificationList(@Param("request") PostUploadRequest request);

    List<InfoForNotification> findInfoForLikeNotificationList(List<LikeRequest> likeRequestList);

    void setNotified(List<LikeRequest> likeRequestList);

    InfoForNotification findInfoForCommentNotification(@Param("request") CommentUploadRequest request);

    InfoForNotification findInfoForFollowNotification(@Param("request") FollowRequest request);
}
