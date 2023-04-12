package com.example.cloproject.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    PARAM_INVALID_ERROR_E001("E001", "File format is not supported"),
    PARAM_INVALID_ERROR_E002("E002", "User does not exist"),
    PARAM_INVALID_ERROR_E003("E003", "File is empty"),
    INTERNAL_SERVER_ERROR("9001", "시스템 에러 발생: 관리자에게 문의 바랍니다.");

    private final String code;

    private final String message;
}
