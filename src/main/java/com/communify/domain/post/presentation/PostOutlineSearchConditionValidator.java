package com.communify.domain.post.presentation;

import com.communify.domain.post.dto.incoming.PostOutlineSearchCondition;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class PostOutlineSearchConditionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PostOutlineSearchCondition.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostOutlineSearchCondition searchCondition = (PostOutlineSearchCondition) target;

        Long categoryId = searchCondition.getCategoryId();
        Long memberId = searchCondition.getMemberId();

        if ((Objects.nonNull(categoryId) && Objects.nonNull(memberId))) {
            errors.reject("null", "categoryId와 memberId 중 하나는 null이어야 합니다.");
        }

        if ((Objects.isNull(categoryId) && Objects.isNull(memberId))) {
            errors.reject("NotNull", "categoryId와 memberId 중 하나는 유효한 값이어야 합니다.");
        }
    }
}
