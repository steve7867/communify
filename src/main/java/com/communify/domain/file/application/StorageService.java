package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileInfo;
import com.communify.domain.file.dto.FileStorageRequest;

import java.util.List;

public interface StorageService {

    List<FileInfo> saveInFileSystem(FileStorageRequest request);
}
