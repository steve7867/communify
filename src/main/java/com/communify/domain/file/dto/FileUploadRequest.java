package com.communify.domain.file.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class FileUploadRequest {

    private final Long postId;
    private final List<MultipartFile> multipartFileList;

    public Boolean isMultipartFileListNull() {
        return Objects.isNull(multipartFileList);
    }

    public Boolean isMultipartFileListEmpty() {
        return multipartFileList.isEmpty();
    }
}
