package com.mj.kimsnote.service.auth;

import com.mj.kimsnote.common.apiException.ApiException;
import com.mj.kimsnote.common.jwt.JwtTokenProvider;
import com.mj.kimsnote.entity.auth.RefreshToken;
import com.mj.kimsnote.repository.auth.RedisRepository;
import com.mj.kimsnote.vo.auth.ReissueToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.mj.kimsnote.common.apiException.ApiExceptionCode.INVALID_REFRESH_TOKEN;
import static com.mj.kimsnote.common.apiException.ApiExceptionCode.NOT_FOUND_REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRepository redisRepository;

    public ReissueToken reissueToken(ReissueToken reissueToken){
        Authentication authentication = jwtTokenProvider.getAuthentication(reissueToken.getAccessToken());

        RefreshToken refreshToken = redisRepository.findById(authentication.getName())
                .orElseThrow(() -> new ApiException(NOT_FOUND_REFRESH_TOKEN));

        if(refreshToken.getRefreshToken().equals(reissueToken.getRefreshToken()) &&  jwtTokenProvider.validateToken(refreshToken.getRefreshToken())){
            String newAccessToken = jwtTokenProvider.newAccessToken(authentication);
            return ReissueToken.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        }
        throw new ApiException(INVALID_REFRESH_TOKEN);
    }
}
