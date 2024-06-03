package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileInfoAndResource;
import com.communify.domain.file.dto.FileUploadRequest;

public interface FileService {

    void uploadFile(FileUploadRequest request);

    FileInfoAndResource getFileInfoAndResource(String storedFilename);

    void deleteFiles(Long postId);
}
