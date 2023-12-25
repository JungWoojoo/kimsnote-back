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

    private boolean isAgree;

    @Builder
    public Member(Long memberId, String email, String password, String name, String birth, Gender gender, String phone, Role role, LoginType loginType, boolean isAgree) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
        this.loginType = loginType;
        this.isAgree = isAgree;
    }

    public Member() {
    }

    public void update(String name, String email){
        this.name = name;
        this.email = email;
    }
}
