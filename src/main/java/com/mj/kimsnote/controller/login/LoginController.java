package com.mj.kimsnote.controller.login;

import com.mj.kimsnote.common.apiResponse.ApiResponse;
import com.mj.kimsnote.service.member.create.MemberAddService;
import com.mj.kimsnote.service.member.read.MemberFindService;
import com.mj.kimsnote.service.member.read.OauthService;
import com.mj.kimsnote.vo.auth.JwtToken;
import com.mj.kimsnote.vo.member.request.JoinMemberRequest;
import com.mj.kimsnote.vo.member.request.LoginMemberRequest;
import com.mj.kimsnote.vo.member.response.MemberResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @GetMapping("/oauth2/code/{loginType}")
    public void getCode(@PathVariable String loginType, @RequestParam String code){
        String url = oauthService.getToken(loginType, code);



    }

//    @GetMapping("/oauth/loginInfo")
//    public String oauthLoginInfo(Authentication authentication) {
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        return attributes.toString();
//    }
}
