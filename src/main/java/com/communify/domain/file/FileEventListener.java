package com.communify.domain.file;

import com.communify.domain.file.dto.FileUploadFailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class FileEventListener {

    private final StorageService storageService;

    @Async
    @EventListener
    public void deleteFiles(FileUploadFailEvent event) {
        File directory = event.getDirectory();
        storageService.deleteFiles(directory);
    }
}
