package com.communify.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostUploadRequest {

    private Long id;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 100, message = "제목은 최대 {0}자까지 허용됩니다.")
    private final String title;

    @NotBlank(message = "본문을 입력해주세요.")
    @Size(max = 20000, message = "본문은 최대 {0}자까지 허용됩니다.")
    private final String content;

    @Size(max = 100)
    @NotNull
    private final List<MultipartFile> fileList;

    @NotNull(message = "카테고리를 지정해주세요.")
    @Positive(message = "카테고리 번호는 양수만 가능합니다.")
    private final Long categoryId;
}
