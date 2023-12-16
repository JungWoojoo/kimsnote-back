package com.mj.kimsnote.controller.member;

import com.mj.kimsnote.service.member.create.MemberAddService;
import com.mj.kimsnote.service.member.read.MemberFindService;
import com.mj.kimsnote.vo.member.request.JoinRequest;
import com.mj.kimsnote.vo.member.request.LoginRequest;
import com.mj.kimsnote.vo.member.response.JoinResponse;
import com.mj.kimsnote.vo.token.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberAddService memberAddService;
    private final MemberFindService memberFindService;

    @PostMapping("/join")
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest joinRequest){
        JoinResponse response = memberAddService.join(joinRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public JwtToken login(@RequestBody LoginRequest loginRequest){
        return memberFindService.login(loginRequest);
    }
}
