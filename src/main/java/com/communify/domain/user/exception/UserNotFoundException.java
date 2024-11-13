package com.communify.domain.user.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class UserNotFoundException extends EntityNotFoundException {

    public static final String EMAIL_NOT_EXISTING_MESSAGE_FORMAT = "이메일이 %s인 회원은 존재하지 않습니다.";
    public static final String USER_ID_NOT_EXISTING_MESSAGE_FORMAT = "회원 번호가 %d인 회원은 존재하지 않습니다.";
    private final String email;
    private final Long userId;

    public UserNotFoundException(String email) {
        super(String.format(EMAIL_NOT_EXISTING_MESSAGE_FORMAT, email));
        this.email = email;
        this.userId = null;
    }

    public UserNotFoundException(Long userId) {
        super(String.format(USER_ID_NOT_EXISTING_MESSAGE_FORMAT, userId));
        this.userId = userId;
        this.email = null;
    }
}
