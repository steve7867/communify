package com.communify.domain.file.dao;

import com.communify.domain.file.dto.FileUploadRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileRepository {

    void insertFiles(FileUploadRequest request);

    void deleteAll(Long postId);
}
