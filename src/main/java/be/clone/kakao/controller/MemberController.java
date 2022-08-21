package be.clone.kakao.controller;

import be.clone.kakao.domain.jwttoken.dto.JwtTokenDto;
import be.clone.kakao.domain.member.dto.LoginRequestDto;
import be.clone.kakao.domain.member.dto.SignupRequestDto;
import be.clone.kakao.jwt.userdetails.UserDetailsImpl;
import be.clone.kakao.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto) {

        Long memberId = memberService.signup(requestDto);

        return ResponseEntity.ok()
                .body(Map.of(
                        "memberId", memberId,
                        "msg", "회원가입 성공"
                ));
    }

    // 로그인
    @PostMapping("/api/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDto requestDto
    ) {

        JwtTokenDto jwtTokenDto = memberService.login(requestDto);

        return ResponseEntity.ok()
                .body(jwtTokenDto);
    }
}
