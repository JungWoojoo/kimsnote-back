package com.mj.kimsnote.vo.member.oauth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Oauth2TokenVO {
        private String access_token;
        private int expires_in;
        private String scope;
        private String token_type;
        private String id_token;

}
