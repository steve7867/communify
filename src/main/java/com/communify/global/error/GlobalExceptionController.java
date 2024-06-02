package com.communify.global.error;

import com.communify.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionController {

    /*
    메서드 인자 유효성 검증 실패 시에 발생하는 MethodArgumentNotValidException를 처리.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);

        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> errorList = bindingResult.getAllErrors();
        String message = errorList.get(0).getDefaultMessage();

        ErrorResponse errorResponse = new ErrorResponse(message);

        ProblemDetail body = e.getBody();
        int status = body.getStatus();
        HttpStatus httpStatus = HttpStatus.resolve(status);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /*
    BusinessException과 그 하위 예외를 모두 처리.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);

        HttpStatus status = e.getStatus();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(errorResponse, status);
    }

    @ResponseStatus(PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ErrorResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);

        return new ErrorResponse(e.getLocalizedMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
    }
}
