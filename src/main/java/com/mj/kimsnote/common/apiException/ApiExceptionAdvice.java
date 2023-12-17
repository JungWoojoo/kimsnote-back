package com.mj.kimsnote.common.apiException;

import com.mj.kimsnote.common.apiResponse.ApiExceptionFormat;
import com.mj.kimsnote.common.apiResponse.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class ApiExceptionAdvice {

    @ExceptionHandler({ApiException.class})
    public ApiResponse<?> exception(HttpServletRequest request, final ApiException e){

        ApiExceptionFormat apiExceptionFormat = ApiExceptionFormat.builder()
                .status(e.getApiExceptionCode().getStatus())
                .errorCode(e.getApiExceptionCode().getErrorCode())
                .errorMessage(e.getApiExceptionCode().getErrorMessage())
                .build();

        return ApiResponse.error(apiExceptionFormat);
    }

    @ExceptionHandler({RuntimeException.class})
    public ApiResponse<?> exceptionHandler(HttpServletRequest request, final RuntimeException e) {

        ApiExceptionFormat apiExceptionFormat = ApiExceptionFormat.builder()
                .errorCode(ApiExceptionCode.RUNTIME_EXCEPTION.getErrorCode())
                .errorMessage(e.getMessage())
                .build();

        e.printStackTrace();

        return ApiResponse.error(apiExceptionFormat);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ApiResponse<?> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {

        ApiExceptionFormat apiExceptionFormat = ApiExceptionFormat.builder()
                .errorCode(ApiExceptionCode.ACCESS_DENIED_EXCEPTION.getErrorCode())
                .errorMessage(e.getMessage())
                .build();

        e.printStackTrace();

        return ApiResponse.error(apiExceptionFormat);
    }

    @ExceptionHandler({Exception.class})
    public ApiResponse<?> exceptionHandler(HttpServletRequest request, final Exception e) {

        ApiExceptionFormat apiExceptionFormat = ApiExceptionFormat.builder()
                .errorCode(ApiExceptionCode.INTERNAL_SERVER_ERROR.getErrorCode())
                .errorMessage(e.getMessage())
                .build();

        e.printStackTrace();

        return ApiResponse.error(apiExceptionFormat);
    }

}
