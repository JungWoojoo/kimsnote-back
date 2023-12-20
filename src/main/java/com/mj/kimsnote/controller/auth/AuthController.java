package com.mj.kimsnote.controller.auth;

import com.mj.kimsnote.common.apiResponse.ApiResponse;
import com.mj.kimsnote.service.auth.AuthService;
import com.mj.kimsnote.vo.auth.ReissueToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public ApiResponse<ReissueToken> reissueToken(@RequestBody ReissueToken reissueToken){
        return ApiResponse.success(authService.reissueToken(reissueToken));
    }
}
