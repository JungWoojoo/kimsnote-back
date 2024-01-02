package com.mj.kimsnote.service.member.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mj.kimsnote.common.apiException.ApiException;
import com.mj.kimsnote.service.member.oauth.impl.GoogleOauth;
import com.mj.kimsnote.vo.member.oauth.Oauth2TokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
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

    public String getUserInfo(String registrationId, String code) throws JsonProcessingException {
        String userInfo;
        if (registrationId.equals("google")) {
            // 1. code이용해 구글 token 얻어오기
            String token = googleOauth.requestToken(code);

            // 2. 얻어온 token 중 id_token 안에 유저 정보 가져오기
            ObjectMapper objectMapper = new ObjectMapper();
            Oauth2TokenVO oauth2TokenVO = objectMapper.readValue(token, Oauth2TokenVO.class);
            String googleAccessToken = oauth2TokenVO.getAccess_token();
            String idToken = oauth2TokenVO.getId_token();

            userInfo = googleOauth.requestUserInfo(googleAccessToken);
        } else {
            throw new ApiException(UNKNOWN_LOGIN_TYPE);
        }
        return userInfo;
    }

}
