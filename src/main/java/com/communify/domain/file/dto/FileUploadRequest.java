package com.communify.domain.file.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Getter
public class FileUploadRequest {

    private final Long postId;
    private final List<UploadFile> uploadFileList;

    public FileUploadRequest(final Long postId, final List<MultipartFile> multipartFileList) {
        this.postId = postId;
        this.uploadFileList = IntStream.range(0, multipartFileList.size())
                .boxed()
                .map(i -> new UploadFile(multipartFileList.get(i), i))
                .toList();
    }

    public Boolean isUploadFileListNull() {
        return Objects.isNull(uploadFileList);
    }

    public Boolean isUploadFileListEmpty() {
        return uploadFileList.isEmpty();
    }
}
