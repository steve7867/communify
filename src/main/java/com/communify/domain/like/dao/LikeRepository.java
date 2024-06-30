package com.communify.domain.like.dao;

import com.communify.domain.like.dto.LikeInfoForNotification;
import com.communify.domain.like.dto.LikeRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LikeRepository {

    Integer insertLikeBulk(List<LikeRequest> likeRequestList);

    List<LikeInfoForNotification> findLikeInfoForNotificationList(List<LikeRequest> likeRequestList);

    void setNotified(List<LikeInfoForNotification> likeInfoForNotificationList);

    Boolean findLiking(Long postId, Long memberId);
}
