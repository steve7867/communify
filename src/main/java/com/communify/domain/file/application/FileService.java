package com.communify.domain.file.application;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    void uploadFile(List<MultipartFile> fileList, Long postId);
}
