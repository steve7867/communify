package com.communify.domain.post.dto;

import com.communify.domain.file.dto.FileInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class PostDetail {

    private final String content;
    private final List<FileInfo> fileInfoList;
}
