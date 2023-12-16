package com.mj.kimsnote.vo.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JoinResponse {
    private Long memberId;
    private String email;
    private String name;
}
