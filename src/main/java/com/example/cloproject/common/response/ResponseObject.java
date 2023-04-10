package com.example.cloproject.common.response;


import lombok.Builder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

@Builder
public class ResponseObject<T> {

    private T data;

    private List<ResponseError> errors;

    public static <T> ResponseObject<T> toResponse(T data) {
        return ResponseObject.<T>builder()
                .data(data)
                .build();
    }

    public static ResponseObject<Object> fieldError(FieldError fieldError) {
        return ResponseObject.builder()
                .errors(Collections.singletonList(ResponseError.fieldError(fieldError)))
                .build();
    }

    public static ResponseObject<Object> error(ErrorCode errorCode) {
        return ResponseObject.builder()
                .errors(Collections.singletonList(ResponseError.ofErrorCode(errorCode)))
                .build();
    }
}
