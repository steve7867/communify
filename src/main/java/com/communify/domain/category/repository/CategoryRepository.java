package com.communify.domain.category.repository;

import com.communify.domain.category.CategoryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryInfo> findAllCategoryList() {
        List<CategoryInfo> categoryList = categoryMapper.findAllCategoryList();
        return Collections.unmodifiableList(categoryList);
    }
}
