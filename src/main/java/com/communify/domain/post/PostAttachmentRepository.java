package com.communify.domain.post;

import com.communify.domain.file.dto.UploadFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostAttachmentRepository {

    void insertAllAttachments(Long postId, List<UploadFile> uploadFileList);

    void deleteAllAttachments(Long postId);
}
