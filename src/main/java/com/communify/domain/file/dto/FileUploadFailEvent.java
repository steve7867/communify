package com.communify.domain.file.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

@Getter
@RequiredArgsConstructor
public class FileUploadFailEvent {

    private final File directory;
}
