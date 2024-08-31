package com.communify.domain.file.application;

import com.communify.domain.file.dto.FileUploadRequest;
import com.communify.domain.file.dto.UploadFile;
import com.communify.domain.file.error.exception.FileUploadFailException;
import com.communify.global.error.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    @Value("${local.file.dir}")
    private String localDir;

    @Override
    public void uploadFiles(final FileUploadRequest request) {
        makeLocalDirectoryIfNotExisting();

        final Long postId = request.getPostId();
        final List<UploadFile> uploadFileList = request.getUploadFileList();

        uploadFileList.forEach(uploadFile -> {
            final String storedFilename = uploadFile.getStoredFilename();
            final String extension = uploadFile.getExtension();
            final MultipartFile multipartFile = uploadFile.getMultipartFile();

            final String filePath = resolveFilePath(postId, storedFilename, extension);
            try {
                multipartFile.transferTo(new File(filePath));
            } catch (IOException e) {
                throw new FileUploadFailException(uploadFile, e);
            }
        });
    }

    private void makeLocalDirectoryIfNotExisting() {
        final File dir = new File(localDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void deleteFiles(final Long postId) {
        final String directoryPath = resolveDirectoryPath(postId);
        final File dir = new File(directoryPath);

        try {
            FileUtils.forceDelete(dir);
        } catch (IOException e) {
            throw new InternalServerException("파일 삭제에 실패했습니다.", e);
        }
    }

    private String resolveDirectoryPath(final Long postId) {
        return localDir + File.separator + postId;
    }

    private String resolveFilePath(final Long postId, final String storedFilename, final String extension) {
        return resolveDirectoryPath(postId) + File.separator + storedFilename + "." + extension;
    }
}
