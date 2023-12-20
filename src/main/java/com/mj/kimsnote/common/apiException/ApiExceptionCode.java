package com.mj.kimsnote.common.apiException;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiExceptionCode {

    /**
     *  System Exception
      */
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"),


    /**
     *   커스텀 Exception
      */
    // 로그인 에러
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "LE0001", "해당 이메일을 찾을 수 없습니다."),

    // 권한, 토큰 에러
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "TE0001", "해당 Refresh Token을 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "TE0002", "Refresh Token이 일치하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private String errorMessage;

    ApiExceptionCode(HttpStatus status, String errorCode) {
        this.status = status;
        this.errorCode = errorCode;
    }

    ApiExceptionCode(HttpStatus status, String errorCode, String errorMessage) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
