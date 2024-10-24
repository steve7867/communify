package com.communify.domain.file;

import com.communify.domain.file.dto.UploadFile;
import com.communify.domain.file.exception.FileDeleteFailException;
import com.communify.domain.file.exception.FileUploadFailException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    @Value("${local.file.directory}")
    private String localDirectoryName;

    @Override
    public void storeFiles(File subDirectory, List<UploadFile> uploadFileList) {
        File directory = resolveDirectory(subDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        uploadFileList.forEach(uploadFile -> {
            MultipartFile multipartFile = uploadFile.getMultipartFile();
            String fullStoredFilename = uploadFile.getFullStoredFilename();

            try {
                multipartFile.transferTo(new File(directory, fullStoredFilename));
            } catch (IOException e) {
                throw new FileUploadFailException(directory, uploadFile, e);
            }
        });
    }

    @Override
    public void deleteFiles(File subDirectory) {
        File directory = resolveDirectory(subDirectory);

        try {
            FileUtils.forceDelete(directory);
        } catch (IOException e) {
            throw new FileDeleteFailException(directory, e);
        }
    }

    private File resolveDirectory(File subDirectory) {
        return new File(localDirectoryName, subDirectory.getPath());
    }
}
