package com.example.cloproject.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    PARAM_INVALID_ERROR_E001("E001", " 파라미터 유효성 검사 오류입니다."),
    PARAM_INVALID_ERROR_E002("E002", " 필수값을 입력해주세요."),
    INTERNAL_SERVER_ERROR("9001", "시스템 에러 발생: 관리자에게 문의 바랍니다.");


    private final String code;

    private final String message;
}
