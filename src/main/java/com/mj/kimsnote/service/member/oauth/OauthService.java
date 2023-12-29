package com.mj.kimsnote.service.member.read.oauth;

import com.mj.kimsnote.common.apiException.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mj.kimsnote.common.apiException.ApiExceptionCode.UNKNOWN_LOGIN_TYPE;

@Service
@RequiredArgsConstructor
public class OauthService {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URL;

    public String getToken(String loginType, String code){
        String result;
        if (loginType.equals("google")) {
            Map<String, Object> params = new HashMap<>();
            params.put("code", code);
            params.put("client_id", GOOGLE_CLIENT_ID);
            params.put("client_secret", GOOGLE_CLIENT_SECRET);
            params.put("redirect_uri", GOOGLE_REDIRECT_URL);
            params.put("grant_type", "authorization_code");

            result = params.entrySet().stream()
                    .map(x -> x.getKey() + "=" + x.getValue())
                    .collect(Collectors.joining("&"));
        } else {
            throw new ApiException(UNKNOWN_LOGIN_TYPE);
        }
        return result;
    }
}
