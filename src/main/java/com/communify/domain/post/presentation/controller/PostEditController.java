package com.communify.domain.post.presentation.controller;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.post.application.PostEditService;
import com.communify.domain.post.dto.PostEditRequest;
import com.communify.domain.post.dto.incoming.PostEditForm;
import com.communify.domain.post.presentation.validator.PostUploadFormValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    @LoginCheck
    public void edit(@PathVariable @NotNull @Positive final Long postId,
                     @ModelAttribute @Valid final PostEditForm form,
                     @MemberId final Long requesterId) {

        final PostEditRequest request = PostEditRequest.builder()
                .postId(postId)
                .title(form.getTitle())
                .content(form.getContent())
                .fileList(Collections.unmodifiableList(form.getFileList()))
                .currentCategoryId(form.getCurrentCategoryId())
                .newCategoryId(form.getNewCategoryId())
                .requesterId(requesterId)
                .build();

        postEditService.editPost(request);
    }

    @InitBinder("postUploadForm")
    public void addPostUploadFormValidator(final WebDataBinder dataBinder) {
        dataBinder.addValidators(postUploadFormValidator);
    }
}
