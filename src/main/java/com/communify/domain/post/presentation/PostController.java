package com.communify.domain.post.presentation;

import com.communify.domain.auth.annotation.CurrentMemberId;
import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.post.application.PostService;
import com.communify.domain.post.dto.PostUploadRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    @LoginCheck
    public void upload(@ModelAttribute @Valid PostUploadRequest request,
                       @CurrentMemberId Long memberId) {

        postService.uploadPost(request, memberId);
    }
}
