package com.communify.domain.catetory.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.catetory.application.CategoryService;
import com.communify.domain.catetory.dto.CategoryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @LoginCheck
    public List<CategoryInfo> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
