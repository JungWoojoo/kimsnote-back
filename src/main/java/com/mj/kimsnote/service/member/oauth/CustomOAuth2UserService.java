package com.mj.kimsnote.service.member.oauth;

import com.mj.kimsnote.common.apiException.ApiException;
import com.mj.kimsnote.common.apiException.ApiExceptionCode;
import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.entity.member.enums.LoginType;
import com.mj.kimsnote.repository.member.MemberRepository;
import com.mj.kimsnote.vo.member.oauth.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

import static com.mj.kimsnote.entity.member.enums.LoginType.KAKAO;
import static com.mj.kimsnote.entity.member.enums.LoginType.NAVER;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스(kakao, google, naver)에서 가져온 유저 정보를 담고있음

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // OAuth 서비스 이름(ex. kakao, naver, google)
        LoginType loginType = getLoginType(registrationId);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth 로그인 시 키(pk)가 되는 값
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(loginType, userNameAttributeName, attributes);

        Member savedMember = saveOrUpdate(extractAttributes, loginType);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(savedMember.getRole().getValue())),
                attributes,
                extractAttributes.getNameAttributeKey()
        );

    }

    private LoginType getLoginType(String registrationId) {
        if(NAVER.equals(registrationId)) {
            return LoginType.NAVER;
        }
        if(KAKAO.equals(registrationId)) {
            return LoginType.KAKAO;
        }
        return LoginType.GOOGLE;
    }

    private Member saveOrUpdate(OAuthAttributes attributes, LoginType loginType) {
        if (!memberRepository.existsByEmail(attributes.getOauth2UserInfo().getEmail())) {
            Member newMember = attributes.toEntity(loginType, attributes.getOauth2UserInfo());
            return memberRepository.save(newMember);
        }
        return memberRepository.findByEmail(attributes.getOauth2UserInfo().getEmail())
                .orElseThrow(() -> new ApiException(ApiExceptionCode.NOT_FOUND_EMAIL));
    }
}
