package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileInfoAndResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    void uploadFile(List<MultipartFile> fileList, Long postId);

    FileInfoAndResource getFileInfoAndResource(String storedFilename);

    void deleteFiles(Long postId);
}
