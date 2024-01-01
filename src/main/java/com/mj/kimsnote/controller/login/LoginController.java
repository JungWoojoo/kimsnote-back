package com.mj.kimsnote.controller.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mj.kimsnote.common.apiResponse.ApiResponse;
import com.mj.kimsnote.service.member.create.MemberAddService;
import com.mj.kimsnote.service.member.oauth.OauthService;
import com.mj.kimsnote.service.member.read.MemberFindService;
import com.mj.kimsnote.vo.auth.JwtToken;
import com.mj.kimsnote.vo.member.request.JoinMemberRequest;
import com.mj.kimsnote.vo.member.request.LoginMemberRequest;
import com.mj.kimsnote.vo.member.response.MemberResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final MemberAddService memberAddService;
    private final MemberFindService memberFindService;
    private final OauthService oauthService;

    @PostMapping("/join")
    public ApiResponse<MemberResponse> join(@RequestBody JoinMemberRequest joinMemberRequest){
        MemberResponse response = memberAddService.join(joinMemberRequest);
        return ApiResponse.success(response);
    }

    @PostMapping("")
    public ApiResponse<JwtToken> login(@RequestBody LoginMemberRequest loginMemberRequest){
        return ApiResponse.success(memberFindService.login(loginMemberRequest));
    }

    @GetMapping("/oauth2/authorization/{loginType}")
    public void oauthLogin(@PathVariable String loginType, HttpServletResponse response) throws IOException {
        String request = oauthService.request(loginType);
        response.sendRedirect(request);
    }

    /**
     * 1. code 이용해 구글 token 얻어옴
     * 2. id_token으로 구글 사용자 정보 얻어옴
     **/
    @GetMapping("/oauth2/code/{registrationId}")
    public String getUserInfo(@PathVariable String registrationId, @RequestParam String code) throws JsonProcessingException {
        String userInfo = oauthService.getUserInfo(registrationId, code);
        log.info("userInfo = {}", userInfo);
        return userInfo;
    }

//    @GetMapping("/oauth/loginInfo")
//    public String oauthLoginInfo(Authentication authentication) {
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        return attributes.toString();
//    }
}
