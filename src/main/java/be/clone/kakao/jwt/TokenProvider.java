package be.clone.kakao.jwt;


import be.clone.kakao.domain.jwttoken.RefreshToken;
import be.clone.kakao.domain.jwttoken.dto.JwtTokenDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.jwt.userdetails.UserDetailsImpl;
import be.clone.kakao.repository.MemberRepository;
import be.clone.kakao.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {
    private static final String BEARER_TYPE = "Bearer ";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;    // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private final Key key;

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenProvider(
            @Value("${jwt.secret}") String secretKey,
            MemberRepository memberRepository,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public JwtTokenDto generateTokenDto(Member member) {

        long now = (new Date().getTime());

        // 액세스 토큰 생성
        String accessToken = BEARER_TYPE + Jwts.builder()
                // 아이디, 닉네임, 만료시간 토큰에 담기
                .setId(member.getMemberId().toString())
                .setSubject(member.getNickname())
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 리프레시 토큰 생성
        String refreshToken = Jwts.builder()
                // 만료시간 토큰에 담기
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 기존에 발급된 리프레시 토큰이 존재한다면 가져오고 없으면 새로 만들기
        RefreshToken refreshTokenObject = refreshTokenRepository.findByMember(member)
                .orElse(new RefreshToken(member));

        // 토큰 설정하고 데이터베이스에 저장
        refreshTokenObject.updateTokenValue(refreshToken);
        refreshTokenRepository.save(refreshTokenObject);

        return new JwtTokenDto(accessToken, refreshToken);
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 payload 읽어오기
        Claims claims = parseClaims(accessToken);
        // 토큰의 memberId 추출
        Long memberId = Long.parseLong(claims.getId());
        // Member 객체 가져오기
        Member member = memberRepository.findById(memberId).orElseThrow();
        // UserDetails 로 변환
        UserDetailsImpl principal = new UserDetailsImpl(member);
        // Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(principal, "", null);
    }

    public Authentication getAuthenticationByRefreshToken(String refreshToken) {
        // 서버에 해당 리프레시 토큰이 존재하는지 확인
        RefreshToken refreshTokenObj = refreshTokenRepository.findByTokenValue(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("서버에 존재하지 않는 리프레시 토큰입니다."));
        // Member 객체 가져오기
        Member member = memberRepository.findById(refreshTokenObj.getMember().getMemberId()).orElseThrow();
        // UserDetails 로 변환
        UserDetailsImpl principal = new UserDetailsImpl(member);
        // Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(principal, "", null);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
            // 토큰 만료 익셉션
            throw expiredJwtException;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
