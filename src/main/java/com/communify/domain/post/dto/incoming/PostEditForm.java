package com.communify.domain.post.dto.incoming;

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
public class PostEditForm {

    @NotBlank
    @Size(min = 1, max = 100)
    private final String title;

    @NotBlank
    @Size(max = 20000)
    private final String content;

    @NotNull
    @Size(max = 100)
    private final List<MultipartFile> fileList;

    @NotNull
    @Positive
    private final Long currentCategoryId;

    @NotNull
    @Positive
    private final Long newCategoryId;
}
