package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileInfo;
import com.communify.domain.file.dto.FileUploadRequest;
import org.springframework.core.io.Resource;

import java.util.List;

public interface StorageService {

    List<FileInfo> saveInFileSystem(FileUploadRequest request);

    void deleteAllFiles(List<FileInfo> fileInfoList);
}
