package com.communify.global.error;

import com.communify.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/*
 * ResponseEntityExceptionHandler는 Http Request 처리 과정에서
 * Spring MVC가 발생시키는 많은 예외를 처리하여 적절한 예외 메시지를 반환해준다.
 * RFC 7807(RFC 9457) 형식에 따라 예외 메시지를 작성한다.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    /*
     * @Valid 어노테이션이 붙은 메서드 인자의 유효성 검증 실패 시에 발생하는 MethodArgumentNotValidException를 처리.
     * MethodArgumentNotValidException의 기본 메시지만으로는 클라이언트가 문제를 파악할 수 없으므로 커스터마이징 해준다.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String message = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return handleExceptionInternal(ex, ProblemDetail.forStatusAndDetail(status, message), headers, status, request);
    }

    /*
     * BusinessException과 그 하위 예외를 모두 처리.
     */
    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);

        return ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
    }

    /*
     * 데이터 무결성 제약을 깨는 데이터 삽입, 수정이 발생할 때 던져지는 DataIntegrityViolationException을 처리
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);

        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, e.getMessage());
    }
}
