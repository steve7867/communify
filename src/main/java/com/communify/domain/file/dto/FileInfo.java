package com.communify.domain.file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class FileInfo {

    private final String storedFilename;
    private final String originalFilename;
    private final String extension;
    private final Integer sequence;
}
