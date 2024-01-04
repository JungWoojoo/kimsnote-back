package com.mj.kimsnote.entity.member;

import com.mj.kimsnote.entity.BaseEntity;
import com.mj.kimsnote.entity.member.enums.Gender;
import com.mj.kimsnote.entity.member.enums.LoginType;
import com.mj.kimsnote.entity.member.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String oauthId;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String name;

    private String birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String profileImg;

    private boolean isAgree;

    @Builder
    public Member(Long memberId, String oauthId, String email, String password, String name, String birth, Gender gender, String phone, Role role, LoginType loginType, String profileImg, boolean isAgree) {
        this.memberId = memberId;
        this.oauthId = oauthId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
        this.loginType = loginType;
        this.profileImg = profileImg;
        this.isAgree = isAgree;
    }


    public Member() {
    }

    public void setRole() {
        role = Role.USER;
    }

    public void setLoginType(String registrationId) {
        switch (registrationId) {
            case "google" -> loginType = LoginType.GOOGLE;
            case "kakao" -> loginType = LoginType.KAKAO;
            case "naver" -> loginType = LoginType.NAVER;
            default -> loginType = LoginType.GENERAL;
        }
    }

    public void setAgree() {
        isAgree = true;
    }
}
