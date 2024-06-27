package com.communify.domain.file.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.file.application.FileService;
import com.communify.domain.file.dto.FileInfo;
import com.communify.domain.file.dto.FileInfoAndResource;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /*
     *  html 페이지에서 <img src="/posts/image/{storedFilename}"/> 태그가 src의 url로 get 요청을 보낸다.
     */
    @GetMapping(value = "/images/{storedFilename}")
    @LoginCheck
    public Resource getFile(@PathVariable @NotNull final String storedFilename) {
        return fileService.getFileInfoAndResource(storedFilename).getResource();
    }

    /*
        HTTP Response Header에서 Content-Disposition 헤더 값을 attachment; filename="image.jpg"와 같이 설정하면
        브라우저는 image.jpg라는 파일을 다운로드한다.
     */
    @GetMapping(value = "/images/{storedFilename}/download")
    @LoginCheck
    public ResponseEntity<Resource> downloadFile(@PathVariable @NotNull final String storedFilename) {
        final FileInfoAndResource fileInfoAndResource = fileService.getFileInfoAndResource(storedFilename);

        final Resource resource = fileInfoAndResource.getResource();
        final FileInfo fileInfo = fileInfoAndResource.getFileInfo();

        final String originalFilename = fileInfo.getOriginalFilename();
        final String encodedOriginalFilename = UriUtils.encode(originalFilename, StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedOriginalFilename + "\"")
                .body(resource);
    }
}
