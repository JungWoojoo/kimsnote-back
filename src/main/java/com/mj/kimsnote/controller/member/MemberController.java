package com.mj.kimsnote.controller.member;

import com.mj.kimsnote.common.apiException.ApiExceptionCode;
import com.mj.kimsnote.common.apiResponse.ApiExceptionFormat;
import com.mj.kimsnote.common.apiResponse.ApiResponse;
import com.mj.kimsnote.service.member.read.MemberFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFindService memberFindService;

    @GetMapping("")
    public ApiResponse<?> findMember(Principal principal){
        if(principal != null) {
            return ApiResponse.success(memberFindService.findMember(principal.getName()));
        }else {
            ApiExceptionCode error = ApiExceptionCode.NOT_PRINCIPAL;
            return ApiResponse.error(new ApiExceptionFormat(error.getStatus(), error.getErrorCode(), error.getErrorMessage()));
        }
    }

}
