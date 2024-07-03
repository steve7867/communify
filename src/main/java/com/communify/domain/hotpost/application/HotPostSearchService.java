package com.communify.domain.hotpost.application;

import com.communify.domain.hotpost.dao.HotPostRepository;
import com.communify.domain.hotpost.dto.AllHotPostSearchCondition;
import com.communify.domain.hotpost.dto.HotPostSearchConditionByCategory;
import com.communify.domain.post.dto.outgoing.PostOutline;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotPostSearchService {

    private final HotPostRepository hotPostRepository;

    @Cacheable(cacheNames = CacheNames.HOT_POST_OUTLINES, sync = true)
    public List<PostOutline> getAllHotPostOutlineList(final AllHotPostSearchCondition searchCond) {
        final List<PostOutline> allHotPostOutlineList = hotPostRepository.findAllHotPostOutlineList(searchCond);
        return Collections.unmodifiableList(allHotPostOutlineList);
    }

    @Cacheable(cacheNames = CacheNames.HOT_POST_OUTLINES, key = "#searchCond.categoryId", sync = true)
    public List<PostOutline> getHotPostOutlineListByCategory(final HotPostSearchConditionByCategory searchCond) {
        final List<PostOutline> hotPostOutlineList = hotPostRepository.findHotPostOutlineByCategoryList(searchCond);
        return Collections.unmodifiableList(hotPostOutlineList);
    }
}
