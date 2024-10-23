package com.communify.domain.file.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Getter
public class UploadFile {

    public static final String DELIMITER = ".";

    private final MultipartFile multipartFile;
    private final String storedFilename;
    private final String originalFilename;
    private final String extension;

    public UploadFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
        this.storedFilename = UUID.randomUUID().toString();
        this.originalFilename = trimExtension(multipartFile.getOriginalFilename());
        this.extension = extractExtension(multipartFile.getOriginalFilename());
    }

    public byte[] getBytes() throws IOException {
        return multipartFile.getBytes();
    }

    public String getContentType() {
        return this.multipartFile.getContentType();
    }

    public Long getSize() {
        return this.multipartFile.getSize();
    }

    public String getFullStoredFilename() {
        return this.storedFilename + DELIMITER + this.extension;
    }

    private String trimExtension(String filename) {
        int indexOfDot = getIndexOfDot(filename);
        return filename.substring(0, indexOfDot);
    }

    private String extractExtension(String filename) {
        int indexOfDot = getIndexOfDot(filename);
        return filename.substring(indexOfDot + 1);
    }

    private int getIndexOfDot(String filename) {
        return filename.lastIndexOf(DELIMITER);
    }
}
