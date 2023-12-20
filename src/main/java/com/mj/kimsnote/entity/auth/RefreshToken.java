package com.mj.kimsnote.entity.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor
@Getter
@RedisHash(value = "refreshToken", timeToLive = 60*60*24*14)
public class RefreshToken {

    @Id
    private String memberId;
    private String refreshToken;

    @Builder
    public RefreshToken(String refreshToken, String memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }

}
