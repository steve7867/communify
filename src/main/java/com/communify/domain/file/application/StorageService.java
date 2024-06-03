package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileInfo;
import com.communify.domain.file.dto.FileUploadRequest;
import org.springframework.core.io.Resource;

import java.util.List;

public interface StorageService {

    List<FileInfo> saveInFileSystem(FileUploadRequest request);

    Resource toResource(FileInfo fileInfo);

    void deleteAllFiles(List<FileInfo> fileInfoList);
}
