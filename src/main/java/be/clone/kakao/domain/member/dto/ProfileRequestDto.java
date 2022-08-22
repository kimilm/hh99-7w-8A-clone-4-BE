package be.clone.kakao.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {
    private String nickname;
    private String profilePic;
    private String introduce;
}
