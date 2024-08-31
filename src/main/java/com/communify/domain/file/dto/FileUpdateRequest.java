package com.communify.domain.file.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class FileUpdateRequest {

    private final Long postId;
    private final List<MultipartFile> multipartFileList;

    public FileUpdateRequest(final Long postId, final List<MultipartFile> multipartFileList) {
        this.postId = postId;
        this.multipartFileList = multipartFileList;
    }
}
