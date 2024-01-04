package com.mj.kimsnote.service.member.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mj.kimsnote.common.apiException.ApiException;
import com.mj.kimsnote.common.jwt.JwtTokenProvider;
import com.mj.kimsnote.entity.auth.RefreshToken;
import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.repository.auth.RedisRepository;
import com.mj.kimsnote.repository.member.MemberRepository;
import com.mj.kimsnote.service.member.UserDetailsImpl;
import com.mj.kimsnote.service.member.oauth.impl.GoogleOauth;
import com.mj.kimsnote.vo.auth.JwtToken;
import com.mj.kimsnote.vo.member.oauth.MemberProfile;
import com.mj.kimsnote.vo.member.oauth.Oauth2TokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static com.mj.kimsnote.common.apiException.ApiExceptionCode.UNKNOWN_LOGIN_TYPE;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final GoogleOauth googleOauth;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRepository redisRepository;


    /** 구글 로그인 **/
    public String request(String loginType){
        String redirectUrl;
        if (loginType.equals("google")) {
            redirectUrl = googleOauth.getOauthRedirectURL();
        } else {
            throw new ApiException(UNKNOWN_LOGIN_TYPE);
        }
        return redirectUrl;
    }

    /**
     * 1. 구글 토큰 발급
     * 2. 구글 유저 정보 **/
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

    /**
     * 1. 유저 정보로 이미 존재하는 회원인지 조회, 미존재시 가입까지.
     * 2. jwt토큰 발급 후 jwt 리턴
     */
    @Transactional
    public JwtToken oauthLogin(String userInfo, String registrationId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MemberProfile memberProfile = objectMapper.readValue(userInfo, MemberProfile.class);
        String email = memberProfile.getEmail();

        boolean isExistEmail = memberRepository.existsByEmail(email);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        Member resultMember = new Member();
        if(memberOptional.isEmpty()){
            Member member = memberProfile.toMember();
            member.setRole();
            member.setLoginType(registrationId);
            member.setAgree();
            memberRepository.save(member);
            resultMember = member;
        } else {
            resultMember = memberOptional.get();
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                resultMember.getEmail(), // Principal (in this case, email is used as the principal)
                "N/A", // Credentials (password or any other sensitive information, but not needed here)
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Authorities
        );

        JwtToken jwtToken = jwtTokenProvider.createToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(authentication.getName())
                .refreshToken(jwtToken.getRefreshToken())
                .build();

        redisRepository.save(refreshToken);

        return jwtToken;
    }
}
