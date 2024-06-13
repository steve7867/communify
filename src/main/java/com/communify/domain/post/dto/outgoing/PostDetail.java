package com.communify.domain.post.dto.outgoing;

import com.communify.domain.file.dto.FileInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class PostDetail {

    private final String content;
    private final List<FileInfo> fileInfoList;
    private final Boolean isLiking;
}
