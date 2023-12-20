package com.mj.kimsnote.service.member.read;

import com.mj.kimsnote.common.jwt.JwtTokenProvider;
import com.mj.kimsnote.repository.member.MemberRepository;
import com.mj.kimsnote.repository.auth.RedisRepository;
import com.mj.kimsnote.vo.member.request.LoginRequest;
import com.mj.kimsnote.vo.auth.JwtToken;
import com.mj.kimsnote.entity.auth.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberFindService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRepository redisRepository;

    public JwtToken login(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);

        JwtToken jwtToken = jwtTokenProvider.createToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(authentication.getName())
                .refreshToken(jwtToken.getRefreshToken())
                .build();

        redisRepository.save(refreshToken);

        return jwtToken;
    }

}
