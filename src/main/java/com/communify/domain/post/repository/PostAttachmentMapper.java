package com.communify.domain.post.repository;

import com.communify.domain.file.dto.UploadFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
interface PostAttachmentMapper {

    void insertAllAttachments(Long postId, List<UploadFile> uploadFileList);

    void deleteAllAttachments(Long postId);
}
