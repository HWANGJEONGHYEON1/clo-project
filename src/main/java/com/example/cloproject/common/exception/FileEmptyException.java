package com.example.cloproject.common.exception;

import com.example.cloproject.common.response.ErrorCode;

public class FileEmptyException extends BusinessException {

    public FileEmptyException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FileEmptyException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
