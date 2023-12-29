//package com.mj.kimsnote.service.member.read.oauth.impl;
//
//import com.mj.kimsnote.service.member.read.oauth.SocialOauth;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class GoogleOauth implements SocialOauth {
//
//    private final String GOOGLE_BASE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
//
//    @Value("${spring.security.oauth2.client.registration.google.client-id}")
//    private String GOOGLE_CLIENT_ID;
//
//    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
//    private String GOOGLE_CLIENT_SECRET;
//
//    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
//    private String GOOGLE_REDIRECT_URL;
//    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
//
//    @Override
//    public String requestToken(String code) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("code", code);
//        params.put("client_id", GOOGLE_CLIENT_ID);
//        params.put("client_secret", GOOGLE_CLIENT_SECRET);
//        params.put("redirect_uri", GOOGLE_REDIRECT_URL);
//        params.put("grant_type", "authorization_code");
//
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);
//
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            return responseEntity.getBody();
//        }
//        return String.valueOf(responseEntity.getStatusCode());
//    }
//}
