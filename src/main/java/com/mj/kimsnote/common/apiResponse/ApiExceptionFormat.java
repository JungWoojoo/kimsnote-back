package com.mj.kimsnote.common.apiResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiExceptionFormat {
    private final String errorCode;
    private final String errorMessage;

    @Builder
    public ApiExceptionFormat(HttpStatus status, String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
