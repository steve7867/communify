package com.communify.domain.push;

import com.communify.domain.push.dto.PushInfoForLike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PushRepository {

    List<String> findTokenOfWritersFollower(Long writerId);

    List<PushInfoForLike> findPushInfoForLikeList(Long postId, List<Long> likerIdList);

    void setPushStateAsSent(List<PushInfoForLike> infoList);

    String findTokenOfPostWriter(Long postId);

    String findTokenOfFollowee(Long followeeId);
}
