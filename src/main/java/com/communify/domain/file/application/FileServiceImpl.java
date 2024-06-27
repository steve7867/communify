package com.communify.domain.file.application;

import com.communify.domain.file.dao.FileRepository;
import com.communify.domain.file.dto.FileInfo;
import com.communify.domain.file.dto.FileInfoAndResource;
import com.communify.domain.file.dto.FileUploadRequest;
import com.communify.domain.file.error.exception.FileNotFoundException;
import com.communify.domain.file.error.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final StorageService storageService;

    @Override
    public void uploadFile(final FileUploadRequest request) {
        if (request.isMultipartFileListNull() || request.isMultipartFileListEmpty()) {
            return;
        }

        final List<FileInfo> fileInfoList = storageService.saveInFileSystem(request);

        fileRepository.insertFileInfoList(fileInfoList);
    }

    @Override
    @Transactional(readOnly = true)
    public FileInfoAndResource getFileInfoAndResource(final String storedFilename) {
        final FileInfo fileInfo = fileRepository.findByStoredFilename(storedFilename)
                .orElseThrow(() -> new FileNotFoundException(storedFilename));

        final Resource resource = storageService.toResource(fileInfo);
        if (!resource.exists()) {
            throw new ResourceNotFoundException(storedFilename);
        }

        return new FileInfoAndResource(fileInfo, resource);
    }

    @Override
    public void deleteFiles(final Long postId) {
        final List<FileInfo> fileInfoList = fileRepository.findAllByPostId(postId);
        storageService.deleteAllFiles(fileInfoList);

        fileRepository.deleteAll(postId);
    }
}
