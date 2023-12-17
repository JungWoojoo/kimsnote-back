package com.mj.kimsnote.service.member;

import com.mj.kimsnote.common.apiException.ApiException;
import com.mj.kimsnote.common.apiException.ApiExceptionCode;
import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.entity.member.enums.Role;
import com.mj.kimsnote.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ApiExceptionCode.NOT_FOUND_EMAIL));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getUsername())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles(Role.USER.getValue()) // 우선 USER로 고정
                .build();
    }
}
