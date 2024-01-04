package com.mj.kimsnote.service.member.read;

import com.mj.kimsnote.common.apiException.ApiException;
import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.repository.member.MemberRepository;
import com.mj.kimsnote.service.member.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.mj.kimsnote.common.apiException.ApiExceptionCode.NOT_FOUND_EMAIL;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_EMAIL));

        return new UserDetailsImpl(member);
    }

}
