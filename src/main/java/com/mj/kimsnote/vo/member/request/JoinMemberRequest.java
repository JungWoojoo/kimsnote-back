package com.mj.kimsnote.vo.member.request;

import com.mj.kimsnote.entity.member.enums.Gender;
import com.mj.kimsnote.entity.member.enums.LoginType;
import lombok.Getter;

@Getter
public class JoinMemberRequest {
    private String email;
    private String password;
    private String name;
    private String birth;
    private Gender gender;
    private String phone;
    private LoginType loginType;
    private boolean isAgree;
}
