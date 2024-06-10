package com.communify.domain.hotpost.application;

import com.communify.domain.category.application.CategoryService;
import com.communify.domain.category.dto.CategoryInfo;
import com.communify.domain.hotpost.dao.HotPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotPostUpdateService {

    private final CategoryService categoryService;
    private final HotPostRepository hotPostRepository;

    @Value("${hot-post.limit-per-category}")
    private Integer hotPostLimitPerCategory;

    @Value("${hot-post.renew-period-in-hour}")
    private Integer renewPeriodInHour;

    @Transactional
    public void updateHotPosts() {
        List<CategoryInfo> categoryInfoList = categoryService.getAllCategories();

        List<Long> renewedHotPostIdList = categoryInfoList.stream()
                .map(CategoryInfo::getId)
                .map(categoryId -> hotPostRepository.findMostViewedPostIdFrom(renewPeriodInHour, categoryId, hotPostLimitPerCategory))
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(() -> new ArrayList<>(categoryInfoList.size() * hotPostLimitPerCategory)));

        if (renewedHotPostIdList.isEmpty()) {
            return;
        }

        hotPostRepository.deleteAllFromHotPost();
        hotPostRepository.insertAllHotPosts(renewedHotPostIdList);
    }
}
