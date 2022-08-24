package be.clone.kakao.controller;

import be.clone.kakao.domain.SimpleMessageDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.domain.member.dto.*;
import be.clone.kakao.jwt.userdetails.UserDetailsImpl;
import be.clone.kakao.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
        LoginResponseDto loginResponseDto = memberService.login(requestDto);

        return ResponseEntity.ok()
                .headers(loginResponseDto.getHeaders())
                .body(loginResponseDto.getProfileResponseDto());
    }

    // 아이디로 정보 조회
    @GetMapping("/api/member/{memberId}")
    public ResponseEntity<?> getProfile(
            @PathVariable Long memberId
    ) {
        ProfileResponseDto responseDto = memberService.getProfile(memberId);

        return ResponseEntity.ok()
                .body(responseDto);
    }

    // 이메일로 정보 조회
    @GetMapping("/api/member")
    public ResponseEntity<?> getProfile(
            @RequestParam("email") String email
    ) {
        ProfileResponseDto responseDto = memberService.getProfile(email);

        return ResponseEntity.ok()
                .body(responseDto);
    }

    // 내 프로필 수정
    @PutMapping("/api/member")
    public ResponseEntity<?> updateProfile(
            @RequestBody ProfileRequestDto profileRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Member member = userDetails.getMember();

        Long memberId = memberService.updateProfile(member, profileRequestDto);

        return ResponseEntity.ok()
                .body(new SimpleMessageDto("수정 성공: memberId = " + memberId));
    }

    // 로그아웃
    @DeleteMapping("/api/logout")
    public ResponseEntity<?> logout(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Member member = userDetails.getMember();

        Long memberId = memberService.logout(member);

        return ResponseEntity.ok()
                .body(new SimpleMessageDto("로그아웃 성공: memberId = " + memberId));
    }

    @GetMapping("/api/reissue")
    public ResponseEntity<?> reissue(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Member member = userDetails.getMember();
        HttpHeaders jwtTokenHeaders = memberService.getJwtTokenHeaders(member);

        return ResponseEntity.ok()
                .headers(jwtTokenHeaders)
                .build();
    }

    @GetMapping("/user/kakao/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        LoginResponseDto loginResponseDto = memberService.kakaoLogin(code, response);
        return ResponseEntity.ok()
                .headers(loginResponseDto.getHeaders())
                .body(loginResponseDto.getProfileResponseDto());
    }
}
