package com.mj.kimsnote.service.member.read;

import com.mj.kimsnote.common.apiException.ApiException;
import com.mj.kimsnote.service.member.read.oauth.GoogleOauth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mj.kimsnote.common.apiException.ApiExceptionCode.UNKNOWN_LOGIN_TYPE;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final GoogleOauth googleOauth;

    public String request(String loginType){
        String redirectUrl;
        if (loginType.equals("google")) {
            redirectUrl = googleOauth.getOauthRedirectURL();
        } else {
            throw new ApiException(UNKNOWN_LOGIN_TYPE);
        }
        return redirectUrl;
    }

    public String getToken(String loginType, String code){
        if (loginType.equals("google")) {
             googleOauth.requestToken(code);
        } else {
            throw new ApiException(UNKNOWN_LOGIN_TYPE);
        }
    }
}
