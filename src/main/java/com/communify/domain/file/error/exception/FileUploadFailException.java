package com.communify.domain.file.error.exception;

import com.communify.domain.file.dto.FileInfo;
import com.communify.global.error.exception.InternalServerException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileUploadFailException extends InternalServerException {

    private final MultipartFile multipartFile;
    private final FileInfo fileInfo;

    public FileUploadFailException(MultipartFile multipartFile, FileInfo fileInfo, Throwable cause) {
        super("파일 업로드에 실패했습니다. 잠시 후 다시 시도해주세요.", cause);
        this.multipartFile = multipartFile;
        this.fileInfo = fileInfo;
    }
}
