package com.communify.domain.category;

import com.communify.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryInfo> getAllCategories() {
        return categoryRepository.findAllCategoryList();
    }
}
