package com.communify.domain.file.dao;

import com.communify.domain.file.dto.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileRepository {

    void insertFileInfoList(List<FileInfo> fileInfoList);
}
