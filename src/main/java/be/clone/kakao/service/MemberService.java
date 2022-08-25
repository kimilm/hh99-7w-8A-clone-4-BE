package be.clone.kakao.service;

import be.clone.kakao.domain.jwttoken.dto.JwtTokenDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.domain.member.dto.*;
import be.clone.kakao.jwt.TokenProvider;
import be.clone.kakao.repository.MemberRepository;
import be.clone.kakao.repository.RefreshTokenRepository;
import be.clone.kakao.util.KakaoUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static be.clone.kakao.jwt.JwtFilter.AUTHORIZATION_HEADER;
import static be.clone.kakao.jwt.JwtFilter.REFRESH_TOKEN_HEADER;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URI;

    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    // 회원가입
    public Long signup(SignupRequestDto requestDto) {
        // 패스워드 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        // 기본 이미지
        String defaultProfilePic = KakaoUtils.getRandomMemberPic();
        // 기본 소개
        String defaultIntroduce = KakaoUtils.getRandomIntroduce();
        // 맴버 객체 생성
        Member member = Member.of(requestDto, encodedPassword, defaultProfilePic, defaultIntroduce);
        // 저장 후 아이디 리턴
        return memberRepository.save(member).getMemberId();
    }

    // 로그인
    public LoginResponseDto login(LoginRequestDto requestDto) {
        // 이메일 검증
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if (member.getKakaoId() != null) throw new IllegalArgumentException("카카오로 가입된 유저입니다.");
        // 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 잘못 입력하셨습니다.");
        }

        HttpHeaders jwtTokenHeaders = getJwtTokenHeaders(member);

        return new LoginResponseDto(jwtTokenHeaders, ProfileResponseDto.of(member));
    }

    // 토큰 헤더 생성
    public HttpHeaders getJwtTokenHeaders(Member member) {
        // 토큰 생성
        JwtTokenDto jwtTokenDto = tokenProvider.generateTokenDto(member);
        // 헤더에 담기
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, jwtTokenDto.getAccessToken());
        headers.add(REFRESH_TOKEN_HEADER, jwtTokenDto.getRefreshToken());

        return headers;
    }

    // 아이디로 프로필 조회
    public ProfileResponseDto getProfile(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원입니다."));

        return ProfileResponseDto.of(member);
    }

    // 이메일로 프로필 조회
    public ProfileResponseDto getProfile(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원입니다."));

        return ProfileResponseDto.of(member);
    }

    // 프로필 수정
    public Long updateProfile(Member member, ProfileRequestDto profileRequestDto) {
        // 로그인한 회원 정보 업데이트
        member.updateMember(profileRequestDto);
        // 저장
        memberRepository.save(member);

        return member.getMemberId();
    }

    // 로그아웃
    @Transactional
    public Long logout(Member member) {

        refreshTokenRepository.deleteByMember(member);

        return member.getMemberId();
    }

    public LoginResponseDto kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getKakaoAccessToken(code);
        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        Member kakaoUser = memberRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {
            // 회원가입
            if (memberRepository.existsByEmail(kakaoUserInfo.getEmail()))
                throw new IllegalArgumentException("이미 가입된 이메일입니다.");
            Member member = new Member(
                    kakaoUserInfo.getEmail(),
                    passwordEncoder.encode(UUID.randomUUID().toString()),
                    kakaoUserInfo.getNickname(),
                    kakaoUserInfo.getImage(),
                    kakaoUserInfo.getId()
            );
            memberRepository.save(member);
            kakaoUser = member;
        }
        HttpHeaders jwtTokenHeaders = getJwtTokenHeaders(kakaoUser);
        return new LoginResponseDto(jwtTokenHeaders, ProfileResponseDto.of(kakaoUser));
    }

    private String getKakaoAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", KAKAO_CLIENT_ID);//내 api키
        body.add("redirect_uri", KAKAO_REDIRECT_URI);
        body.add("code", code);//카카오로부터 받은 인가코드

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);//httpentity객체를 만들어서 보냄
        RestTemplate rt = new RestTemplate();//서버 대 서버 요청을 보냄
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );//리스폰스 받기

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();//바디부분
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);//json형태를 객체형태로 바꾸기
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();//서버 대 서버 요청을 보냄
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String image = jsonNode.get("properties")
                .get("profile_image").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        return new KakaoUserInfoDto(id, nickname, image, email);
    }
}
