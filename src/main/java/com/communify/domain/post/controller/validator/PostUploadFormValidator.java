package com.communify.domain.post.controller.validator;

import com.communify.domain.post.dto.PostUploadForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class PostUploadFormValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return PostUploadForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final PostUploadForm request = (PostUploadForm) target;

        verifyFileList(request.getFileList(), errors);
    }

    private void verifyFileList(final List<MultipartFile> fileList, final Errors errors) {
        for (MultipartFile file : fileList) {
            final String contentType = file.getContentType();
            if (!isImageType(contentType)) {
                errors.rejectValue("fileList", "ContentType", "이미지 파일만 허용됩니다.");
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename.length() > 100) {
                errors.rejectValue("fileList", "MaxSize", new Object[100], "파일 이름은 최대 {0}자까지만 허용됩니다.");
            }
        }
    }

    private boolean isImageType(final String contentType) {
        return contentType.startsWith("image/");
    }
}
