package com.communify.domain.post.dto;

import com.communify.domain.file.dto.FileInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class PostDetail {

    private final String content;

    @JsonIgnore
    private final LocalDateTime createdDateTime;

    @JsonIgnore
    private final Boolean isHot;

    private final List<FileInfo> fileInfoList;
}
