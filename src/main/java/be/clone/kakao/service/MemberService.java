package be.clone.kakao.service;

import be.clone.kakao.domain.jwttoken.dto.JwtTokenDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.domain.member.dto.LoginRequestDto;
import be.clone.kakao.domain.member.dto.SignupRequestDto;
import be.clone.kakao.jwt.TokenProvider;
import be.clone.kakao.repository.MemberRepository;
import be.clone.kakao.util.MemberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    // 회원가입
    public Long signup(SignupRequestDto requestDto) {
        // 패스워드 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        // 기본 이미지
        String defaultProfilePic = MemberUtils.getPic();
        // 맴버 객체 생성
        Member member = Member.of(requestDto, encodedPassword, defaultProfilePic);
        // 저장 후 아이디 리턴
        return memberRepository.save(member).getMemberId();
    }

    // 로그인
    public JwtTokenDto login(LoginRequestDto requestDto) {
        // 이메일 검증
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));
        // 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 잘못 입력하셨습니다.");
        }
        // 토큰 생성
        JwtTokenDto jwtTokenDto = tokenProvider.generateTokenDto(member);

        return jwtTokenDto;
    }
}
