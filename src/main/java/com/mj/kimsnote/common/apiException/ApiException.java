package com.mj.kimsnote.common.apiException;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private ApiExceptionCode apiExceptionCode;

    public ApiException(ApiExceptionCode apiExceptionCode) {
        super(apiExceptionCode.getErrorMessage());
        this.apiExceptionCode = apiExceptionCode;
    }

}
