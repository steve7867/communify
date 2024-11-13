package com.communify.domain.category.repository;

import com.communify.domain.category.CategoryInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
interface CategoryMapper {

    List<CategoryInfo> findAllCategoryList();
}
