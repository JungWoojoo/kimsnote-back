package com.mj.kimsnote.service.member.oauth.impl;

import com.mj.kimsnote.service.member.oauth.SocialOauth;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleOauth implements SocialOauth {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
//
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URL;

//    @Value("${spring.security.oauth2.client.registration.google.scope}")
//    private String GOOGLE_SCOPE;

    /** 구글 로그인 후 code값 리다이렉트 **/
    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile email");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_REDIRECT_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        String GOOGLE_BASE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
        return GOOGLE_BASE_URL + "?" + parameterString;
    }

    /** 받아온 구글 code로 token 얻어옴 **/
    @Override
    public String requestToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("code", code);
        request.put("client_id", GOOGLE_CLIENT_ID);
        request.put("client_secret", GOOGLE_CLIENT_SECRET);
        request.put("redirect_uri", GOOGLE_REDIRECT_URL);
        request.put("grant_type", "authorization_code");

        String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_TOKEN_URL, request, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }

        return String.valueOf(responseEntity.getStatusCode());
    }

    /** id_token 값으로 유저 정보 **/
    @Override
    public String requestUserInfo(String googleAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + googleAccessToken);
        HttpEntity<String> header = new HttpEntity<>(headers);

//        String GOOGLE_ID_TOKEN_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=";
        String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(
//                GOOGLE_ID_TOKEN_URL+idToken,
                GOOGLE_USERINFO_URL,
                HttpMethod.GET,
                header,
                String.class
        );
        return responseEntity.getBody();
    }
}