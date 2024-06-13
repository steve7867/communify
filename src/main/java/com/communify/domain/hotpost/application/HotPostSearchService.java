package com.communify.domain.hotpost.application;

import com.communify.domain.category.application.CategoryService;
import com.communify.domain.category.dto.CategoryInfo;
import com.communify.domain.hotpost.dao.HotPostRepository;
import com.communify.domain.post.dto.outgoing.PostOutline;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotPostSearchService {

    private final CategoryService categoryService;
    private final ObjectProvider<HotPostSearchService> objectProvider;
    private final HotPostRepository hotPostRepository;

    @Value("${hot-post.limit-per-category}")
    private Integer hotPostLimitPerCategory;

    @Cacheable(cacheNames = CacheNames.HOT_POST_OUTLINES, sync = true)
    public List<PostOutline> getHotPostOutlineList() {
        List<CategoryInfo> categoryInfoList = categoryService.getAllCategories();

        return categoryInfoList
                .stream()
                .map(CategoryInfo::getId)
                .map(objectProvider.getObject()::getHotPostOutlineListByCategory)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(() -> new ArrayList<>(categoryInfoList.size() * hotPostLimitPerCategory)));
    }

    @Cacheable(cacheNames = CacheNames.HOT_POST_OUTLINES, key = "#categoryId", sync = true)
    public List<PostOutline> getHotPostOutlineListByCategory(Long categoryId) {
        return hotPostRepository.findHotPostOutlinesByCategory(categoryId, hotPostLimitPerCategory);
    }
}
