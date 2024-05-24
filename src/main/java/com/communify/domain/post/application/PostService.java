package com.communify.domain.post.application;

import com.communify.domain.post.dao.PostRepository;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.dto.PostSearchCondition;
import com.communify.domain.post.dto.PostUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void uploadPost(PostUploadRequest request,
                           Long memberId) {

        postRepository.insertPost(request, memberId);

        List<MultipartFile> multipartFileList = Collections.unmodifiableList(request.getFileList());
        Long postId = request.getId();
        fileService.uploadFile(multipartFileList, postId); //todo: import문 추가
    }

    @Transactional(readOnly = true) //todo:캐싱 적용
    public List<PostOutline> getPostOutlineList(PostSearchCondition searchCond) {
        return postRepository.findAllPostOutlineBySearchCond(searchCond);
    }
}
