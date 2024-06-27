package com.communify.domain.file.error.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends EntityNotFoundException {

    public static final String MESSAGE_FORMAT = "%s 파일은 존재하지 않습니다.";
    private final String storedFilename;

    public ResourceNotFoundException(String storedFilename) {
        super(String.format(MESSAGE_FORMAT, storedFilename));
        this.storedFilename = storedFilename;
    }
}
