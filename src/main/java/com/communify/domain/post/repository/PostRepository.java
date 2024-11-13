package com.communify.domain.post.repository;

import com.communify.domain.post.dto.HotPostChecker;
import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.dto.PostUploadDto;
import com.communify.domain.post.exception.InvalidPostAccessException;
import com.communify.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final PostMapper postMapper;

    public void insertPost(PostUploadDto dto) {
        postMapper.insertPost(dto);
    }

    @Transactional(readOnly = true)
    public List<PostOutline> findHotPostOutlines(Long postId, Integer searchSize) {
        List<PostOutline> hotPostOutlineList = postMapper.findHotPostOutlines(postId, searchSize);
        return Collections.unmodifiableList(hotPostOutlineList);
    }

    @Transactional(readOnly = true)
    public List<PostOutline> findPostOutlinesByCategory(Long categoryId, Long lastPostId, Integer searchSize) {
        List<PostOutline> postOutlineList = postMapper.findPostOutlinesByCategory(categoryId, lastPostId, searchSize);
        return Collections.unmodifiableList(postOutlineList);
    }

    @Transactional(readOnly = true)
    public List<PostOutline> findPostOutlinesByMember(Long writerId, Long lastPostId, Integer searchSize) {
        List<PostOutline> postOutlineList = postMapper.findPostOutlinesByUser(writerId, lastPostId, searchSize);
        return Collections.unmodifiableList(postOutlineList);
    }

    @Transactional(readOnly = true)
    public PostDetail findPostDetail(Long postId) {
        return postMapper.findPostDetail(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    public void incViewCount(Long postId, Integer count) {
        postMapper.incViewCount(postId, count);
    }

    public void incLikeCount(Long postId, Integer count) {
        postMapper.incLikeCount(postId, count);
    }

    public void incCommentCount(Long postId, Integer count) {
        postMapper.incCommentCount(postId, count);
    }

    public void decCommentCount(Long postId, Integer count) {
        postMapper.decCommentCount(postId, count);
    }

    public void editPost(Long postId, String title, String content, Long categoryId, Long requesterId) {
        boolean isEdited = postMapper.editPost(postId, title, content, categoryId, requesterId);
        if (!isEdited) {
            throw new InvalidPostAccessException(postId, requesterId);
        }
    }

    public void deletePost(Long postId, Long requesterId) {
        boolean isDeleted = postMapper.deletePost(postId, requesterId);
        if (!isDeleted) {
            throw new InvalidPostAccessException(postId, requesterId);
        }
    }

    public void promoteToHot(Long postId) {
        postMapper.promoteToHot(postId);
    }

    @Transactional(readOnly = true)
    public HotPostChecker findHotPostChecker(Long postId) {
        return postMapper.findHotPostChecker(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }
}
