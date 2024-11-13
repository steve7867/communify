package com.communify.domain.post.controller;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.UserId;
import com.communify.domain.post.controller.validator.PostUploadFormValidator;
import com.communify.domain.post.dto.PostUploadForm;
import com.communify.domain.post.service.PostEditService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

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
    public void edit(@PathVariable @NotNull @Positive Long postId,
                     @ModelAttribute @Valid PostUploadForm form,
                     @UserId Long requesterId) {

        String title = form.getTitle();
        String content = form.getContent();
        List<MultipartFile> multipartFileList = Collections.unmodifiableList(form.getFileList());
        Long categoryId = form.getCategoryId();

        postEditService.editPost(postId, title, content, multipartFileList, categoryId, requesterId);
    }

    @InitBinder("postUploadForm")
    public void addPostUploadFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(postUploadFormValidator);
    }

    @PostMapping("/{postId}/like")
    @ResponseStatus(OK)
    @LoginCheck
    public void like(@PathVariable @NotNull @Positive Long postId,
                     @UserId Long likerId) {

        postEditService.like(postId, likerId);
    }
}
