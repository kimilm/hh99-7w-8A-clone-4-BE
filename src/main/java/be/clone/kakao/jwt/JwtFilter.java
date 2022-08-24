package be.clone.kakao.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request에서 토큰 뽑아오기
        String refreshToken = resolveRefreshToken(request);
        String accessToken = resolveAccessToken(request);
        // 적합한 리프레시 토큰이라면 reissue 수행
        if (refreshToken != null && tokenProvider.validateToken(refreshToken)) {
            // 리프레시 토큰으로부터 authentication 객체 얻어오기
            Authentication authentication = tokenProvider.getAuthenticationByRefreshToken(refreshToken);
            // 받아온 Authentication 객체 시큐리티 컨텍스트 홀더에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 적합한 엑세스 토큰이라면 authentication 에 추가
        else if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
            // 토큰으로부터 Authentication 객체 얻어오기
            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            // 받아온 Authentication 객체 시큐리티 컨텍스트 홀더에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // 헤더에서 토큰을 뽑은 메서드
    private String resolveRefreshToken(HttpServletRequest request) {
        // authorization 헤더에서 토큰 추출
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
        // 없으면 null
        return refreshToken;
    }

    // 헤더에서 토큰을 뽑은 메서드
    private String resolveAccessToken(HttpServletRequest request) {
        // authorization 헤더에서 토큰 추출
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        // 접두사 분리
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
