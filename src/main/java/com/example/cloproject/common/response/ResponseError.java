package com.example.cloproject.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

@Builder
@Getter
public class ResponseError {

    private String code;

    private String title;

    private String detail;

    public static ResponseError ofErrorCode(ErrorCode errorCode) {
        return ResponseError.builder()
                .code(errorCode.getCode())
                .title(errorCode.name())
                .detail(errorCode.getMessage())
                .build();
    }

    public static ResponseError fieldError(FieldError fieldError) {
        if (ObjectUtils.isEmpty(fieldError) || !StringUtils.hasText(fieldError.getCode())) {
            return ResponseError.ofErrorCode(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        String codeType = fieldError.getCode();
        String errorDefMsg = fieldError.getDefaultMessage();

        StringBuilder errorMessage = new StringBuilder();
        ErrorCode errorCode;

        switch (codeType) {
            case "NotNull":
            case "NotEmpty":
            case "NotBlank":
                errorCode = ErrorCode.PARAM_INVALID_ERROR_E002;
                errorMessage.append("(").append(errorDefMsg).append(")").append(ErrorCode.PARAM_INVALID_ERROR_E002.getMessage());
                break;
            default:
                errorCode = ErrorCode.PARAM_INVALID_ERROR_E001;
                errorMessage.append("(").append(errorDefMsg).append(")").append(ErrorCode.PARAM_INVALID_ERROR_E001.getMessage());
                break;
        }


        return ResponseError.builder()
                .code(errorCode.getCode())
                .title(errorCode.name())
                .detail(errorMessage.toString())
                .build();
    }
}
