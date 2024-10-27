package com.communify.domain.post.service;

import com.communify.domain.file.StorageService;
import com.communify.domain.file.dto.FileUploadFailEvent;
import com.communify.domain.file.dto.UploadFile;
import com.communify.domain.file.exception.FileUploadFailException;
import com.communify.domain.post.PostAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostAttachmentService {

    private static final String directoryName = "/posts";

    private final PostAttachmentRepository postAttachmentRepository;
    private final StorageService storageService;
    private final ApplicationEventPublisher eventPublisher;

    public void savePostAttachments(Long postId, List<MultipartFile> multipartFileList) {
        if (multipartFileList == null || multipartFileList.isEmpty()) {
            return;
        }

        File directory = new File(directoryName, String.valueOf(postId));
        List<UploadFile> uploadFileList = multipartFileList
                .stream()
                .map(UploadFile::new)
                .toList();

        try {
            storageService.storeFiles(directory, uploadFileList);
        } catch (FileUploadFailException e) {
            eventPublisher.publishEvent(new FileUploadFailEvent(directory));
            throw e;
        }

        postAttachmentRepository.insertAllAttachments(postId, uploadFileList);
    }

    public void updateFiles(Long postId, List<MultipartFile> multipartFileList) {
        deleteFiles(postId);
        savePostAttachments(postId, multipartFileList);
    }

    public void deleteFiles(Long postId) {
        File directory = new File(directoryName, String.valueOf(postId));

        storageService.deleteFiles(directory);
        postAttachmentRepository.deleteAllAttachments(postId);
    }
}
