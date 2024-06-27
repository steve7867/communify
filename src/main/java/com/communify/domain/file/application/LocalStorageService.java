package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileInfo;
import com.communify.domain.file.dto.FileUploadRequest;
import com.communify.domain.file.error.exception.FileUploadFailException;
import com.communify.global.error.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    private final ResourceLoader resourceLoader;

    @Value("${local.file.dir}")
    private String localDir;

    @Override
    public List<FileInfo> saveInFileSystem(final FileUploadRequest request) {
        final File dir = new File(localDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        final Long postId = request.getPostId();
        final List<MultipartFile> multipartFileList = request.getMultipartFileList();

        return IntStream.range(0, multipartFileList.size())
                .boxed()
                .map(i -> {
                    final MultipartFile multipartFile = multipartFileList.get(i);
                    final FileInfo fileInfo = new FileInfo(multipartFile.getOriginalFilename(), postId, i);

                    try {
                        final String filePath = resolveFilePath(fileInfo);
                        multipartFile.transferTo(new File(filePath));
                    } catch (IOException e) {
                        throw new FileUploadFailException(multipartFile, fileInfo, e);
                    }

                    return fileInfo;
                }).toList();
    }

    @Override
    public Resource toResource(final FileInfo fileInfo) {
        final String filePath = resolveFilePath(fileInfo);
        final String fileUri = "file:///" + filePath;
        return resourceLoader.getResource(fileUri);
    }

    @Override
    public void deleteAllFiles(final List<FileInfo> fileInfoList) {
        fileInfoList.forEach(fileInfo -> {
            final String filePath = resolveFilePath(fileInfo);
            final File file = new File(filePath);

            deleteFile(file);
        });
    }

    private void deleteFile(final File file) {
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            throw new InternalServerException("파일 삭제에 실패했습니다.", e);
        }
    }

    private String resolveFilePath(final FileInfo fileInfo) {
        final String storedFilename = fileInfo.getStoredFilename();
        final String extension = fileInfo.getExtension();

        return localDir + File.separator + storedFilename + "." + extension;
    }
}
