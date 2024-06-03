package com.communify.domain.post.presentation;

import com.communify.domain.post.dto.incoming.PostUploadForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class PostUploadFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PostUploadForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostUploadForm request = (PostUploadForm) target;

        verifyFileList(request.getFileList(), errors);
    }

    private void verifyFileList(List<MultipartFile> fileList, Errors errors) {
        for (MultipartFile file : fileList) {
            String contentType = file.getContentType();
            if (!isImageType(contentType)) {
                errors.rejectValue("fileList", "ContentType", "이미지 파일만 허용됩니다.");
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename.length() > 100) {
                errors.rejectValue("fileList", "MaxSize", new Object[100], "파일 이름은 최대 {0}자까지만 허용됩니다.");
            }
        }
    }

    private boolean isImageType(String contentType) {
        return contentType.startsWith("image/");
    }
}
