package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    List<FileInfo> saveInFileSystem(List<MultipartFile> multipartFileList, Long postId);
}
