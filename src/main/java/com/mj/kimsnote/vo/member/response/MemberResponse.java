package com.mj.kimsnote.vo.member.response;

import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.entity.member.enums.Gender;
import com.mj.kimsnote.entity.member.enums.LoginType;
import com.mj.kimsnote.entity.member.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MemberResponse {
    private Long memberId;
    private String email;
    private String name;
    private String birth;
    private Gender gender;
    private String phone;
    private Role role;
    private LoginType loginType;
    private boolean isAgree;
    private String createdDate;
    private String updatedDate;

    public static MemberResponse toVo(Member member){
        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .name(member.getName())
                .birth(member.getBirth())
                .gender(member.getGender())
                .phone(member.getPhone())
                .role(member.getRole())
                .loginType(member.getLoginType())
                .isAgree(member.isAgree())
                .createdDate(member.getCreatedDate())
                .updatedDate(member.getUpdatedDate())
                .build();
    }
}
