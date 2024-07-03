package com.communify.domain.category.dao;

import com.communify.domain.category.dto.CategoryInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryRepository {

    List<CategoryInfo> findAllCategoryList();
}
