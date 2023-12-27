package com.mj.kimsnote.service.member.read;

import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.entity.member.enums.LoginType;
import com.mj.kimsnote.entity.member.enums.OAuthAttributes;
import com.mj.kimsnote.repository.member.MemberRepository;
import com.mj.kimsnote.vo.member.request.MemberProfile;
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
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스(kakao, google, naver)에서 가져온 유저 정보를 담고있음

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // OAuth 서비스 이름(ex. kakao, naver, google)

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth 로그인 시 키(pk)가 되는 값

        Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들

        MemberProfile memberProfile = OAuthAttributes.extract(registrationId, attributes); // registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어 줌
        memberProfile.setLoginType(LoginType.valueOf(registrationId));

        Member member = saveOrUpdate(memberProfile);

        Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, memberProfile, registrationId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                customAttribute,
                userNameAttributeName);
    }

    private Map<String, Object> customAttribute(Map<String, Object> attributes, String userNameAttributeName, MemberProfile memberProfile, String registrationId) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("loginType", registrationId);
        customAttribute.put("name", memberProfile.getName());
        customAttribute.put("email", memberProfile.getEmail());
        return customAttribute;
    }

    private Member saveOrUpdate(MemberProfile memberProfile) {
        Member member = memberRepository.findByEmailAndLoginType(memberProfile.getEmail(), memberProfile.getLoginType())
                .map(m -> m.update(memberProfile.getName(), memberProfile.getEmail()))
                .orElse(memberProfile.toMember());
        return memberRepository.save(member);
    }

}
