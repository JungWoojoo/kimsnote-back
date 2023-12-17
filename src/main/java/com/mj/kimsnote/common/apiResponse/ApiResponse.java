package com.mj.kimsnote.common.apiResponse;

import com.mj.kimsnote.common.apiException.ApiException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String ERROR = "error";

    private String status;
    private T data;
    private ApiExceptionFormat exception;

    public ApiResponse(String status, T data, ApiExceptionFormat exception) {
        this.status = status;
        this.data = data;
        this.exception = exception;
    }

    // api호출 성공 타입
    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(SUCCESS, data, null);
    }

    // 유효하지 않은 데이터로 api호출 실패시
    public static ApiResponse<?> fail(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse<>(FAIL, errors, null);
    }

    // 예외 발생으로 api호출 실패시
    public static ApiResponse<?> error(ApiExceptionFormat exception){
        return new ApiResponse<>(ERROR, null, exception);
    }

}
