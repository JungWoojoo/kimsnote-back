package com.mj.kimsnote.entity.member.enums;

import com.mj.kimsnote.vo.member.request.MemberProfile;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", (attribute) -> {
        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setName((String)attribute.get("name"));
        memberProfile.setEmail((String)attribute.get("email"));

        return memberProfile;
    }),

    NAVER("naver", (attribute) -> {
        MemberProfile memberProfile = new MemberProfile();

        Map<String, String> responseValue = (Map)attribute.get("response");

        memberProfile.setName(responseValue.get("name"));
        memberProfile.setEmail(responseValue.get("email"));

        return memberProfile;
    }),

    KAKAO("kakao", (attribute) -> {
        MemberProfile memberProfile = new MemberProfile();
        Map<String, Object> account = (Map)attribute.get("kakao_account");
        Map<String, String> profile = (Map)account.get("profile");

        memberProfile.setName(profile.get("nickname"));
        memberProfile.setEmail((String)account.get("email"));

        return memberProfile;
    });

    private final String registrationId; // 로그인한 서비스(ex) google, naver..)
    private final Function<Map<String, Object>, MemberProfile> of; // 로그인한 사용자의 정보를 통하여 UserProfile을 가져옴

    OAuthAttributes(String registrationId, Function<Map<String, Object>, MemberProfile> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static MemberProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(value -> registrationId.equals(value.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
