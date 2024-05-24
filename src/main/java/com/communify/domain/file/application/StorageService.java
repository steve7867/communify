package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileInfo;
import com.communify.domain.file.dto.FileStorageRequest;
import org.springframework.core.io.Resource;

import java.util.List;

public interface StorageService {

    List<FileInfo> saveInFileSystem(FileStorageRequest request);

    Resource toResource(FileInfo fileInfo);

    void deleteAllFiles(Long postId);
}
