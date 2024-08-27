package com.communify.domain.push.dao;

import com.communify.domain.comment.dto.CommentUploadRequest;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.domain.post.dto.PostUploadRequest;
import com.communify.domain.push.dto.PushInfoForComment;
import com.communify.domain.push.dto.PushInfoForFollow;
import com.communify.domain.push.dto.PushInfoForLike;
import com.communify.domain.push.dto.PushInfoForPostUpload;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PushRepository {

    List<PushInfoForPostUpload> findPushInfoForPostUploadList(@Param("request") PostUploadRequest request);

    List<PushInfoForLike> findPushInfoForLikeList(List<LikeRequest> requestList);

    void setPushStateAsSent(List<PushInfoForLike> infoList);

    PushInfoForComment findPushInfoForComment(@Param("request") CommentUploadRequest request);

    PushInfoForFollow findPushInfoForFollow(@Param("request") FollowRequest request);
}
