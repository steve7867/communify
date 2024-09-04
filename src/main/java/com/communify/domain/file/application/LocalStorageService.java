package com.communify.domain.file.application;

import com.communify.domain.file.dto.UploadFile;
import com.communify.domain.file.error.exception.FileDeleteFailException;
import com.communify.domain.file.error.exception.FileUploadFailException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    @Value("${local.file.directory}")
    private String localDirectoryName;

    @Override
    public void storeFiles(final File subDirectory, final List<UploadFile> uploadFileList) {
        final File directory = resolveDirectory(subDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        uploadFileList.forEach(uploadFile -> {
            final MultipartFile multipartFile = uploadFile.getMultipartFile();
            final String fullStoredFilename = uploadFile.getFullStoredFilename();

            try {
                multipartFile.transferTo(new File(directory, fullStoredFilename));
            } catch (IOException e) {
                throw new FileUploadFailException(directory, uploadFile, e);
            }
        });
    }

    @Override
    public void deleteFiles(final File subDirectory) {
        final File directory = resolveDirectory(subDirectory);

        try {
            FileUtils.forceDelete(directory);
        } catch (final IOException e) {
            throw new FileDeleteFailException(directory, e);
        }
    }

    private File resolveDirectory(final File subDirectory) {
        return new File(localDirectoryName, subDirectory.getPath());
    }
}
