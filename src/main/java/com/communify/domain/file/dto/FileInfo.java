package com.communify.domain.file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(force = true)
public class FileInfo {

    private final String storedFilename;
    private final String originalFilename;
    private final String extension;
    private final Long postId;
    private final Integer sequence;

    public FileInfo(final String originalFilename,
                    final Long postId,
                    final Integer sequence) {

        this.storedFilename = UUID.randomUUID().toString();
        this.originalFilename = trimExtension(originalFilename);
        this.extension = extractExtension(originalFilename);
        this.postId = postId;
        this.sequence = sequence;
    }

    private String trimExtension(final String filename) {
        final int indexOfDot = filename.lastIndexOf(".");
        return filename.substring(0, indexOfDot);
    }

    private String extractExtension(final String filename) {
        final int indexOfDot = filename.lastIndexOf(".");
        return filename.substring(indexOfDot + 1);
    }
}
