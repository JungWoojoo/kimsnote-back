package com.mj.kimsnote.service.member.create;

import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.repository.member.MemberRepository;
import com.mj.kimsnote.vo.member.request.JoinRequest;
import com.mj.kimsnote.vo.member.response.JoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberAddService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public JoinResponse join(JoinRequest joinRequest){
        String password = passwordEncoder.encode(joinRequest.getPassword());
        Member member = toEntity(joinRequest, password);
        memberRepository.save(member);
        return toVo(member);
    }

    private static Member toEntity(JoinRequest joinRequest, String password){
        return Member.builder()
                .email(joinRequest.getEmail())
                .password(password)
                .name(joinRequest.getName())
                .phone(joinRequest.getPhone())
                .build();
    }

    private static JoinResponse toVo(Member member){
        return JoinResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
