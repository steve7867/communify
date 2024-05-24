package com.communify.domain.file.presentation;

import com.communify.domain.file.application.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /*
     *  html 페이지에서 <img src="/posts/image/{storedFilename}"/> 태그가 src의 url로 get 요청을 보낸다.
     */
    @GetMapping(value = "/images/{storedFilename}")
    public Resource getFile(@PathVariable String storedFilename) {
        return fileService.getFileInfoAndResource(storedFilename).getResource();
    }
}
