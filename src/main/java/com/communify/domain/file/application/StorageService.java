package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileUploadRequest;

public interface StorageService {

    void uploadFiles(FileUploadRequest request);

    void deleteFiles(Long postId);
}
