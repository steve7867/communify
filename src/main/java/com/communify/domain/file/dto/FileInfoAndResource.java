package com.communify.domain.file.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

@Getter
@RequiredArgsConstructor
public class FileInfoAndResource {

    private final FileInfo fileInfo;
    private final Resource resource;
}
