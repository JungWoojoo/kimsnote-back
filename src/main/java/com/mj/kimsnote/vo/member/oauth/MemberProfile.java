package com.mj.kimsnote.vo.member.oauth;

import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.entity.member.enums.LoginType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MemberProfile {
    private String id;
    private String email;
    private String name;
    private String picture;
    private LoginType loginType;

    public Member toMember(){
        return Member.builder()
                .oauthId(this.id)
                .email(this.email)
                .name(this.name)
                .profileImg(this.picture)
                .build();
    }

    // OAuth2User 인터페이스의 메서드를 구현
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", this.id);
        attributes.put("email", this.email);
        attributes.put("name", this.name);
        attributes.put("picture", this.picture);
        // 다른 속성들도 필요에 따라 추가

        return attributes;
    }
}
