package com.communify.domain.like.dao;

import com.communify.domain.like.dto.LikeNotificationInfo;
import com.communify.domain.like.dto.LikeRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LikeRepository {

    Integer insertLikeBulk(List<LikeRequest> likeRequestList);

    /**
     * 알림 전송에 필요한 정보(fcm 토큰, 좋아요 누른 회원의 이름)를 가져온다.
     * 알림 전송이 불가능하거나(e.g. 게시글 작성자가 탈퇴한 경우, 작성자의 fcm 토큰 부재)
     * 알림을 전송할 필요가 없는(e.g. 게시글 작성자와 좋아요를 누른 회원이 동일, 이미 알림 전송됨)
     * 대상은 제외한다.
     *
     * @param likeRequestList
     * @return {@code List<LikeNotificationInfo>}
     */
    List<LikeNotificationInfo> findFilteredLikeNotificationInfoList(List<LikeRequest> likeRequestList);

    void setNotified(List<LikeNotificationInfo> likeNotificationInfoList);
}
