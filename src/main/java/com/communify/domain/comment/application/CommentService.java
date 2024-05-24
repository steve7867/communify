package com.communify.domain.comment.application;

import com.communify.domain.comment.dao.CommentRepository;
import com.communify.domain.comment.dto.CommentInfo;
import com.communify.domain.comment.dto.CommentUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void addComment(CommentUploadRequest request) {
        commentRepository.insert(request);
        //todo: 이벤트 처리
    }

    @Transactional(readOnly = true) //todo: 캐싱 적용
    public List<CommentInfo> getComments(Long postId) {
        List<CommentInfo> commentInfoList = commentRepository.findAllCommentsByPostId(postId);
        return Collections.unmodifiableList(commentInfoList);
    }
}
