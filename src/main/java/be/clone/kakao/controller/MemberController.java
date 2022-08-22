package be.clone.kakao.controller;

import be.clone.kakao.domain.SimpleMessageDto;
import be.clone.kakao.domain.member.dto.LoginRequestDto;
import be.clone.kakao.domain.member.dto.ProfileResponseDto;
import be.clone.kakao.domain.member.dto.SignupRequestDto;
import be.clone.kakao.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(
            @RequestBody SignupRequestDto requestDto
    ) {
        Long memberId = memberService.signup(requestDto);

        return ResponseEntity.ok()
                .body(new SimpleMessageDto("회원가입 성공: memberId = " + memberId));
    }

    // 로그인
    @PostMapping("/api/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDto requestDto
    ) {
        HttpHeaders headers = memberService.login(requestDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new SimpleMessageDto("로그인 성공"));
    }

    // 내 정보 조회
    @GetMapping("/api/member/{memberId}")
    public ResponseEntity<?> getProfile(
            @PathVariable Long memberId
    ) {
        ProfileResponseDto responseDto = memberService.getProfile(memberId);

        return ResponseEntity.ok()
                .body(responseDto);
    }
}
