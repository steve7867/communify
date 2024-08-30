package com.communify.domain.file.application;

import com.communify.domain.file.dao.FileRepository;
import com.communify.domain.file.dto.FileInfo;
import com.communify.domain.file.dto.FileUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final StorageService storageService;

    public void uploadFile(final FileUploadRequest request) {
        if (request.isMultipartFileListNull() || request.isMultipartFileListEmpty()) {
            return;
        }

        final List<FileInfo> fileInfoList = storageService.saveInFileSystem(request);

        fileRepository.insertFileInfoList(fileInfoList);
    }

    public void deleteFiles(final Long postId) {
        final List<FileInfo> fileInfoList = fileRepository.findAllByPostId(postId);
        storageService.deleteAllFiles(fileInfoList);

        fileRepository.deleteAll(postId);
    }
}
