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
import java.util.Map;

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

    /** Oauth2 로그인 **/
    @GetMapping("/oauth2/authorization/{loginType}")
    public void oauthAuthorization(@PathVariable String loginType, HttpServletResponse response) throws IOException {
        String request = oauthService.request(loginType);
        response.sendRedirect(request);
    }

    /** code 이용해 Oauth token 얻고 프론트 callback 경로로 redirect **/
    @GetMapping("/oauth2/code/{registrationId}")
    public void getToken(@PathVariable String registrationId, @RequestParam String code, HttpServletResponse response) throws IOException {
        String frontUrl = oauthService.getToken(registrationId, code);
        log.info("frontUrl = {}", frontUrl);
        response.sendRedirect(frontUrl);
    }

    @PostMapping("/oauth2")
    public ApiResponse<JwtToken> oauth2Login(@RequestBody Map<String, Object> param) throws JsonProcessingException {
        String registrationId = String.valueOf(param.get("registrationId"));
        String token = String.valueOf(param.get("token"));
        String userInfo = oauthService.getUserInfo(registrationId, token);
        log.info("userInfo = {}", userInfo);
        JwtToken jwtToken = oauthService.oauthLogin(userInfo, registrationId);
        log.info("jwtToken = {}", jwtToken);
        return ApiResponse.success(jwtToken);
    }
}
