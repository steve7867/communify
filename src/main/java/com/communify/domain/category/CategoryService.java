package com.communify.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryInfo> getAllCategories() {
        final List<CategoryInfo> categoryInfoList = categoryRepository.findAllCategoryList();
        return Collections.unmodifiableList(categoryInfoList);
    }
}
