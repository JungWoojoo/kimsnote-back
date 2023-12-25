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
    // 에러 샘플
    NOT_FOUND_SAMPLE(HttpStatus.NOT_FOUND, "EX0001", "해당 샘플을 찾을 수 없습니다."),

    // 로그인 에러
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "LE0001", "해당 이메일을 찾을 수 없습니다."),

    // 권한, 토큰 에러
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "TE0001", "해당 Refresh Token을 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "TE0002", "Refresh Token이 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TE003", "유효한 토큰이 아닙니다."),
    NOT_EXPIRATION_TOKEN(HttpStatus.UNAUTHORIZED, "TE004", "토큰이 만료 되었습니다."),
    NOT_PRINCIPAL(HttpStatus.UNAUTHORIZED, "TE005", "인증된 사용자 정보가 없습니다."),
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
