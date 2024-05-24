package com.communify.domain.comment.application;

import com.communify.domain.comment.dao.CommentRepository;
import com.communify.domain.comment.dto.CommentUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void addComment(CommentUploadRequest request) {
        commentRepository.insert(request);
        //todo: 이벤트 처리
    }
}
