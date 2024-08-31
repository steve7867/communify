package com.communify.domain.file.error.exception;

import com.communify.domain.file.dto.UploadFile;
import com.communify.global.error.exception.InternalServerException;
import lombok.Getter;

@Getter
public class FileUploadFailException extends InternalServerException {

    public static final String MESSAGE = "파일 업로드에 실패했습니다. 잠시 후 다시 시도해주세요.";
    private final UploadFile uploadFile;

    public FileUploadFailException(final UploadFile uploadFile, final Throwable cause) {
        super(MESSAGE, cause);
        this.uploadFile = uploadFile;
    }
}
