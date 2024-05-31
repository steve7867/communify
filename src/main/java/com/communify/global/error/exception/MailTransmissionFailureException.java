package com.communify.global.error.exception;

public class MailTransmissionFailureException extends InternalServerException {

    public MailTransmissionFailureException(Throwable cause) {
        super("메일 전송에 실패하였습니다.", cause);
    }
}
