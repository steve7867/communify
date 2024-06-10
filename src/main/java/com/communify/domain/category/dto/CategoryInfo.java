package com.communify.domain.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class CategoryInfo {

    private final Long id;
    private final String name;
}
