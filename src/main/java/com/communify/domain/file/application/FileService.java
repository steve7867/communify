package com.communify.domain.file.application;

import com.communify.domain.file.dao.FileRepository;
import com.communify.domain.file.dto.FileUpdateRequest;
import com.communify.domain.file.dto.FileUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final StorageService storageService;

    public void uploadFiles(final FileUploadRequest request) {
        if (request.isUploadFileListNull() || request.isUploadFileListEmpty()) {
            return;
        }

        storageService.uploadFiles(request);
        fileRepository.insertFiles(request);
    }

    public void updateFiles(final FileUpdateRequest request) {
        deleteFiles(request.getPostId());
        uploadFiles(new FileUploadRequest(request.getPostId(), request.getMultipartFileList()));
    }

    public void deleteFiles(final Long postId) {
        storageService.deleteFiles(postId);
        fileRepository.deleteAll(postId);
    }
}
