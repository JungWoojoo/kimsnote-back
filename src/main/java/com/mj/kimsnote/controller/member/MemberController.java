package com.mj.kimsnote.controller.member;

import com.mj.kimsnote.common.apiException.ApiExceptionCode;
import com.mj.kimsnote.common.apiResponse.ApiExceptionFormat;
import com.mj.kimsnote.common.apiResponse.ApiResponse;
import com.mj.kimsnote.service.member.create.MemberAddService;
import com.mj.kimsnote.service.member.read.MemberDetailService;
import com.mj.kimsnote.service.member.read.MemberFindService;
import com.mj.kimsnote.vo.auth.JwtToken;
import com.mj.kimsnote.vo.member.request.JoinMemberRequest;
import com.mj.kimsnote.vo.member.request.LoginMemberRequest;
import com.mj.kimsnote.vo.member.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberAddService memberAddService;
    private final MemberFindService memberFindService;
    private final MemberDetailService memberDetailService;

    @PostMapping("/join")
    public ApiResponse<MemberResponse> join(@RequestBody JoinMemberRequest joinMemberRequest){
        MemberResponse response = memberAddService.join(joinMemberRequest);
        return ApiResponse.success(response);
    }

    @PostMapping("/login")
    public ApiResponse<JwtToken> login(@RequestBody LoginMemberRequest loginMemberRequest){
        return ApiResponse.success(memberFindService.login(loginMemberRequest));
    }

    @GetMapping("")
    public ApiResponse<?> findMember(Principal principal){
        if(principal != null) {
            return ApiResponse.success(memberFindService.findMember(principal.getName()));
        }else {
            ApiExceptionCode error = ApiExceptionCode.NOT_PRINCIPAL;
            return ApiResponse.error(new ApiExceptionFormat(error.getStatus(), error.getErrorCode(), error.getErrorMessage()));
        }
    }

    @GetMapping("/oauth/loginInfo")
    public String oauthLoginInfo(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return attributes.toString();
    }
}
