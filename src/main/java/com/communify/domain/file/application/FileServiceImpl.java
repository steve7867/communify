package com.communify.domain.file.application;

import com.communify.domain.file.dao.FileRepository;
import com.communify.domain.file.dto.FileInfo;
import com.communify.domain.file.dto.FileStorageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final StorageService storageService;

    @Override
    public void uploadFile(List<MultipartFile> multipartFileList, Long postId) {
        if (Objects.isNull(multipartFileList) || multipartFileList.isEmpty()) {
            return;
        }

        FileStorageRequest fileStorageRequest = new FileStorageRequest(postId, multipartFileList);
        List<FileInfo> fileInfoList = storageService.saveInFileSystem(fileStorageRequest);

        fileRepository.insertFileInfoList(fileInfoList);
    }
}
