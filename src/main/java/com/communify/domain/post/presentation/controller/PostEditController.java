package com.communify.domain.post.presentation.controller;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.post.application.PostEditService;
import com.communify.domain.post.dto.PostEditRequest;
import com.communify.domain.post.dto.incoming.PostUploadForm;
import com.communify.domain.post.presentation.validator.PostUploadFormValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostEditController {

    private final PostEditService postEditService;
    private final PostUploadFormValidator postUploadFormValidator;

    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    @LoginCheck
    public void edit(@PathVariable Long postId,
                     @ModelAttribute @Valid PostUploadForm form,
                     @MemberId Long memberId) {

        PostEditRequest request = PostEditRequest.builder()
                .postId(postId)
                .title(form.getTitle())
                .content(form.getContent())
                .fileList(Collections.unmodifiableList(form.getFileList()))
                .categoryId(form.getCategoryId())
                .memberId(memberId)
                .build();

        postEditService.editPost(request);
    }

    @InitBinder("postUploadForm")
    public void addPostUploadFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(postUploadFormValidator);
    }
}
