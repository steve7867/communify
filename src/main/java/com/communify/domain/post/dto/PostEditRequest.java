package com.communify.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
public class PostEditRequest {

    private final Long postId;
    private final String title;
    private final String content;
    private final List<MultipartFile> fileList;
    private final Long currentCategoryId;
    private final Long newCategoryId;

    private final Long memberId;

    public Boolean isEditingCategory() {
        return !Objects.equals(currentCategoryId, newCategoryId);
    }
}
