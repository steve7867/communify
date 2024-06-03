package com.communify.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostEditRequest {

    private final Long postId;
    private final String title;
    private final String content;
    private final List<MultipartFile> fileList;
    private final Long categoryId;

    private final Long memberId;
}
