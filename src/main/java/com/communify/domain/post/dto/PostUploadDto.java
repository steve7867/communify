package com.communify.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class PostUploadDto {

    private final Long id;
    private final String title;
    private final String content;
    private final List<MultipartFile> multipartFileList;
    private final Long categoryId;
    private final Long writerId;
    private final String writerName;
}
