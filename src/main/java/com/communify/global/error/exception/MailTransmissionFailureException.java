package com.communify.global.error.exception;

public class MailTransmissionFailureException extends InternalServerException {

    public static final String MESSAGE = "메일 전송에 실패하였습니다.";

    public MailTransmissionFailureException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
