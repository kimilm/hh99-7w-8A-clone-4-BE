package be.clone.kakao.jwt.exception;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException expiredJwtException) {
            // 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            Claims claims = expiredJwtException.getClaims();

            Map<Object, Object> values = Map.of(
                    Claims.EXPIRATION, false,
                    Claims.ID, claims.getId(),
                    Claims.SUBJECT, claims.getSubject(),
                    "message", "만료된 JWT token 입니다."
            );

            PrintWriter writer = response.getWriter();
            writer.write(new Gson().toJson(values));
        }
    }
}
