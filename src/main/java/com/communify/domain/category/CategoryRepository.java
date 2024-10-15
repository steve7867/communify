package com.communify.domain.category;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryRepository {

    List<CategoryInfo> findAllCategoryList();
}
