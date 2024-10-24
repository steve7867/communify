package com.communify.domain.file.exception;

import com.communify.global.error.exception.InternalServerException;
import lombok.Getter;

import java.io.File;

@Getter
public class FileDeleteFailException extends InternalServerException {

    private static final String MESSAGE_PREFIX = "파일 삭제에 실패했습니다. 잠시 후 다시 시도해주세요. 디렉토리 경로 = ";

    private final File directory;

    public FileDeleteFailException(File directory, Throwable cause) {
        super(MESSAGE_PREFIX + directory.getPath(), cause);
        this.directory = directory;
    }
}
