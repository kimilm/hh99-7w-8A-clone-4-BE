package be.clone.kakao.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private HttpHeaders headers;
    private ProfileResponseDto profileResponseDto;
}
