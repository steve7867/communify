package com.communify.domain.catetory.application;

import com.communify.domain.catetory.dao.CategoryRepository;
import com.communify.domain.catetory.dto.CategoryInfo;
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
        List<CategoryInfo> categoryInfoList = categoryRepository.findAll();
        return Collections.unmodifiableList(categoryInfoList);
    } 
}
