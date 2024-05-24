package com.communify.domain.file.error.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class FileNotFoundException extends EntityNotFoundException {

    private final String storedFilename;

    public FileNotFoundException(String storedFilename) {
        super(String.format("%s 파일은 존재하지 않습니다.", storedFilename));
        this.storedFilename = storedFilename;
    }
}
