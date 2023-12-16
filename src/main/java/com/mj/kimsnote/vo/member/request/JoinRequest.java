package com.mj.kimsnote.vo.member.request;

import lombok.Getter;

@Getter
public class JoinRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
}
