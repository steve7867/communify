package com.communify.domain.post.dto;

import com.communify.domain.file.dto.FileInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.util.List;

@Getter
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class PostDetail {

    private final String content;
    private final List<FileInfo> fileInfoList;
}
