package com.communify.global.error.exception;

public class MailSendFailException extends InternalServerException {

    public static final String MESSAGE = "메일 전송에 실패하였습니다.";

    public MailSendFailException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
