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
    public List<FileInfo> saveInFileSystem(FileUploadRequest request) {
        Long postId = request.getPostId();
        List<MultipartFile> multipartFileList = request.getMultipartFileList();

        File dir = new File(localDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return IntStream.range(0, multipartFileList.size())
                .boxed()
                .map(i -> {
                    MultipartFile multipartFile = multipartFileList.get(i);
                    FileInfo fileInfo = FileInfo.of(multipartFile.getOriginalFilename(), postId, i);
                    String filePath = resolveFilePath(fileInfo);

                    try {
                        multipartFile.transferTo(new File(filePath));
                    } catch (IOException e) {
                        throw new FileUploadFailException(multipartFile, fileInfo, e);
                    }

                    return fileInfo;
                }).toList();
    }

    @Override
    public Resource toResource(FileInfo fileInfo) {
        String filePath = resolveFilePath(fileInfo);
        String fileUri = "file:///" + filePath;
        return resourceLoader.getResource(fileUri);
    }

    @Override
    public void deleteAllFiles(List<FileInfo> fileInfoList) {
        fileInfoList.forEach(fileInfo -> {
            String filePath = resolveFilePath(fileInfo);
            File file = new File(filePath);

            deleteFile(file);
        });
    }

    private void deleteFile(File file) {
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            throw new InternalServerException("파일 삭제에 실패했습니다.", e);
        }
    }

    private String resolveFilePath(FileInfo fileInfo) {
        String storedFilename = fileInfo.getStoredFilename();
        String extension = fileInfo.getExtension();

        return localDir + File.separator + storedFilename + "." + extension;
    }
}
