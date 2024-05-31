package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileInfo;
import com.communify.domain.file.dto.FileStorageRequest;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    private final ResourceLoader resourceLoader;

    @Value("${local.file.dir}")
    private String localDir;

    @Override
    public List<FileInfo> saveInFileSystem(FileStorageRequest request) {
        List<MultipartFile> multipartFileList = request.getMultipartFileList();
        Long postId = request.getPostId();

        if (multipartFileList.isEmpty()) {
            return Collections.emptyList();
        }

        File dir = makeDir(postId);

        return IntStream.range(0, multipartFileList.size())
                .boxed()
                .map(i -> {
                    MultipartFile multipartFile = multipartFileList.get(i);
                    FileInfo fileInfo = FileInfo.of(multipartFile.getOriginalFilename(), postId, i);
                    String storedFilename = fileInfo.getStoredFilename();
                    String extension = fileInfo.getExtension();

                    try {
                        multipartFile.transferTo(new File(dir, storedFilename + "." + extension));
                    } catch (IOException e) {
                        deleteDir(dir);
                        throw new FileUploadFailException(multipartFile, fileInfo, e);
                    }

                    return fileInfo;
                }).toList();
    }

    @Override
    public Resource toResource(FileInfo fileInfo) {
        Long postId = fileInfo.getPostId();
        String storedFilename = fileInfo.getStoredFilename();
        String extension = fileInfo.getExtension();
        String dirPath = resolveDirPath(postId);

        String fileUri = "file:///" + dirPath + storedFilename + "." + extension;
        return resourceLoader.getResource(fileUri);
    }

    @Override
    public void deleteAllFiles(Long postId) {
        String dirPath = resolveDirPath(postId);
        File dir = new File(dirPath);
        deleteDir(dir);
    }

    private File makeDir(Long postId) {
        String dirPath = resolveDirPath(postId);
        File dir = new File(dirPath);

        if (!dir.exists()) {
            dir.mkdir();
        }

        return dir;
    }

    private void deleteDir(File dir) {
        try {
            FileUtils.forceDelete(dir);
        } catch (IOException e) {
            throw new InternalServerException("디렉토리 삭제에 실패했습니다.", e);
        }
    }

    private String resolveDirPath(Long postId) {
        return localDir + postId + File.separator;
    }
}
