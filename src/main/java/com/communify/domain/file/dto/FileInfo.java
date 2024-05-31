package com.communify.domain.file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class FileInfo {

    private final String storedFilename;
    private final String originalFilename;
    private final String extension;
    private final Long postId;
    private final Integer sequence;

    public static FileInfo of(String originalFilename, Long postId, Integer sequence) {
        String extension = extractExtension(originalFilename);
        originalFilename = trimExtension(originalFilename);
        String storedFilename = UUID.randomUUID().toString();

        return new FileInfo(storedFilename, originalFilename, extension, postId, sequence);
    }

    private static String trimExtension(String filename) {
        int i = filename.lastIndexOf(".");
        return filename.substring(0, i);
    }

    private static String extractExtension(String filename) {
        int i = filename.lastIndexOf(".");
        return filename.substring(i + 1);
    }
}
