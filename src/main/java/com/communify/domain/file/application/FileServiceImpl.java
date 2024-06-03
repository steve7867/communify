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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final StorageService storageService;

    @Override
    public void uploadFile(FileUploadRequest request) {
        List<MultipartFile> multipartFileList = request.getMultipartFileList();
        if (Objects.isNull(multipartFileList) || multipartFileList.isEmpty()) {
            return;
        }

        List<FileInfo> fileInfoList = storageService.saveInFileSystem(request);

        fileRepository.insertFileInfoList(fileInfoList);
    }

    @Override
    @Transactional(readOnly = true)
    public FileInfoAndResource getFileInfoAndResource(String storedFilename) {
        FileInfo fileInfo = fileRepository.findByStoredFilename(storedFilename)
                .orElseThrow(() -> new FileNotFoundException(storedFilename));

        Resource resource = storageService.toResource(fileInfo);
        if (!resource.exists()) {
            throw new ResourceNotFoundException(storedFilename);
        }

        return new FileInfoAndResource(fileInfo, resource);
    }

    @Override
    public void deleteFiles(Long postId) {
        List<FileInfo> fileInfoList = fileRepository.findAllByPostId(postId);
        storageService.deleteAllFiles(fileInfoList);

        fileRepository.deleteAll(postId);
    }
}
