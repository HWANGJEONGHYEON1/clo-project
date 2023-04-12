package com.example.cloproject.common.response;


import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

@Builder
@Getter
public class ResponseObject<T> {

    private T data;

    private List<ResponseError> errors;

    public static ResponseObject<Object> error(ErrorCode errorCode) {
        return ResponseObject.builder()
                .errors(Collections.singletonList(ResponseError.ofErrorCode(errorCode)))
                .build();
    }
}
