package com.communify.domain.file.exception;

import com.communify.domain.file.dto.UploadFile;
import com.communify.global.error.exception.InternalServerException;
import lombok.Getter;

import java.io.File;

@Getter
public class FileUploadFailException extends InternalServerException {

    private static final String MESSAGE = "파일 업로드에 실패했습니다. 잠시 후 다시 시도해주세요.";

    private final File directory;
    private final UploadFile uploadFile;

    public FileUploadFailException(File directory, UploadFile uploadFile, Throwable cause) {
        super(MESSAGE, cause);
        this.directory = directory;
        this.uploadFile = uploadFile;
    }
}
