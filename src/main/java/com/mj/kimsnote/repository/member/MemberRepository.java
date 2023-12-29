package com.mj.kimsnote.repository.member;

import com.mj.kimsnote.entity.member.Member;
import com.mj.kimsnote.entity.member.enums.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndLoginType(String email, LoginType loginType);

    boolean existsByEmail(String email);
}
