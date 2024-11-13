package com.communify.domain.post.repository;

import com.communify.domain.file.dto.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostAttachmentRepository {

    private final PostAttachmentMapper postAttachmentMapper;

    public void insertAllAttachments(Long postId, List<UploadFile> uploadFileList) {
        postAttachmentMapper.insertAllAttachments(postId, uploadFileList);
    }

    public void deleteAllAttachments(Long postId) {
        postAttachmentMapper.deleteAllAttachments(postId);
    }
}
