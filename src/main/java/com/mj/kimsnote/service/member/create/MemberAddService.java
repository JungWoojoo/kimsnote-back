package com.mj.kimsnote.service.member.create;

import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.entity.member.enums.Gender;
import com.mj.kimsnote.entity.member.enums.Role;
import com.mj.kimsnote.repository.member.MemberRepository;
import com.mj.kimsnote.vo.member.request.JoinMemberRequest;
import com.mj.kimsnote.vo.member.response.MemberResponse;
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
    public MemberResponse join(JoinMemberRequest joinMemberRequest){
        String password = passwordEncoder.encode(joinMemberRequest.getPassword());
        Member member = toEntity(joinMemberRequest, password);
        memberRepository.save(member);
        return MemberResponse.toVo(member);
    }

    private static Member toEntity(JoinMemberRequest joinMemberRequest, String password){
        return Member.builder()
                .email(joinMemberRequest.getEmail())
                .password(password)
                .name(joinMemberRequest.getName())
                .birth(joinMemberRequest.getBirth())
                .phone(joinMemberRequest.getPhone())
                .gender(joinMemberRequest.getGender())
                .role(Role.USER)
                .loginType(joinMemberRequest.getLoginType())
                .isAgree(joinMemberRequest.isAgree())
                .build();
    }

}
