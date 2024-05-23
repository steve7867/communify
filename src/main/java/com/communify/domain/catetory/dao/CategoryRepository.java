package com.communify.domain.catetory.dao;

import com.communify.domain.catetory.dto.CategoryInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryRepository {

    List<CategoryInfo> findAll();
}
