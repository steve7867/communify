package com.communify.domain.file;

import com.communify.domain.file.dto.UploadFile;

import java.io.File;
import java.util.List;

public interface StorageService {

    void storeFiles(File directory, List<UploadFile> uploadFileList);

    void deleteFiles(File directory);
}
