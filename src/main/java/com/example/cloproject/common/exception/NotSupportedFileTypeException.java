package com.example.cloproject.common.exception;

import com.example.cloproject.common.response.ErrorCode;

public class NotSupportedFileTypeException extends BusinessException {


    public NotSupportedFileTypeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotSupportedFileTypeException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
