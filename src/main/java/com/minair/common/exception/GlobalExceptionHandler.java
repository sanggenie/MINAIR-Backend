package com.minair.common.exception;

import com.minair.common.response.BaseResponse;
import com.minair.common.response.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> handleGlobalException(GlobalException e) {
        ResponseStatus status = e.getStatus();
        return ResponseEntity.status(status.getHttpStatus())
                .body(new BaseResponse<>(status));
    }
}
