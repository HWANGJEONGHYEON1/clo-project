package com.example.cloproject.common.exception;

import com.example.cloproject.common.response.ErrorCode;
import com.example.cloproject.common.response.ResponseError;
import com.example.cloproject.common.response.ResponseObject;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;

import static com.example.cloproject.common.response.ErrorCode.PARAM_INVALID_ERROR_E004;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public List<ResponseError> defaultExceptionHandler(Exception e) {
        log.error("defaultExceptionHandler : {}", e.getMessage());
        return ResponseObject.error(ErrorCode.INTERNAL_SERVER_ERROR)
                .getErrors();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ResponseError> webExchangeBusinessExceptionHandler(BusinessException e) {
        log.warn("businessExceptionHandler : {}", e.getDetail());
        return ResponseObject.error(e.getErrorCode())
                .getErrors();
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ResponseError> webExchangeJsonMappingExceptionHandler(JsonMappingException e) {

        log.warn("businessExceptionHandler : {}", e.getMessage());
        return ResponseObject.error(PARAM_INVALID_ERROR_E004)
                .getErrors();
    }
}
