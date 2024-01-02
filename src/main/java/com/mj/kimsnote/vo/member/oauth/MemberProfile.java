package com.mj.kimsnote.vo.member.oauth;

import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.entity.member.enums.LoginType;
import lombok.Getter;

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
}
