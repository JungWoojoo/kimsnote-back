//package com.mj.kimsnote.vo.member.oauth;
//
//import com.mj.kimsnote.entity.member.Member;
//import com.mj.kimsnote.entity.member.enums.LoginType;
//import com.mj.kimsnote.entity.member.enums.Role;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Map;
//
//@Getter
//@Builder
//public class OAuthAttributes2 {
//    private Map<String, Object> attributes;
//    private String nameAttributeKey;
//    private String name;
//    private String email;
//    private LoginType loginType;
//
//    public static OAuthAttributes2 of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//        if("naver".equals(registrationId)) {
//            return ofNaver("id", attributes);
//        } else if ("kakao".equals(registrationId)) {
//            return ofKakao("id", attributes);
//        }
//
//        return ofGoogle(userNameAttributeName, attributes);
//    }
//
//    private static OAuthAttributes2 ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        return OAuthAttributes2.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .loginType(LoginType.GOOGLE)
//                .build();
//    }
//
//    private static OAuthAttributes2 ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//        return OAuthAttributes2.builder()
//                .name((String) response.get("name"))
//                .email((String) response.get("email"))
//                .attributes(response)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    private static OAuthAttributes2 ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
//        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
//        Map<String, Object> account = (Map<String, Object>) attributes.get("profile");
//
//        return OAuthAttributes2.builder()
//                .name((String) account.get("nickname"))
//                .email((String) response.get("email"))
//                .attributes(response)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    public Member toEntity() {
//        return Member.builder()
//                .name(name)
//                .email(email)
//                .role(Role.USER)
//                .build();
//    }
//}
