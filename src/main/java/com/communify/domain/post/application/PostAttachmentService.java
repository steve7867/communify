package com.communify.domain.post.application;

import com.communify.domain.file.application.StorageService;
import com.communify.domain.file.dto.FileUploadFailEvent;
import com.communify.domain.file.dto.UploadFile;
import com.communify.domain.file.error.exception.FileUploadFailException;
import com.communify.domain.post.dao.PostAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostAttachmentService {

    private final PostAttachmentRepository postAttachmentRepository;
    private final StorageService storageService;

    private final ApplicationEventPublisher eventPublisher;

    @Value("${post.attachment.directory}")
    private String postAttachmentDirectoryName;

    public void savePostAttachments(final Long postId, final List<MultipartFile> multipartFileList) {
        if (Objects.isNull(multipartFileList) || multipartFileList.isEmpty()) {
            return;
        }

        final File directory = new File(postAttachmentDirectoryName, String.valueOf(postId));
        final List<UploadFile> uploadFileList = multipartFileList
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

    public void updateFiles(final Long postId, final List<MultipartFile> multipartFileList) {
        deleteFiles(postId);
        savePostAttachments(postId, multipartFileList);
    }

    public void deleteFiles(final Long postId) {
        final File directory = new File(postAttachmentDirectoryName, String.valueOf(postId));

        storageService.deleteFiles(directory);
        postAttachmentRepository.deleteAllAttachments(postId);
    }
}
