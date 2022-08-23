package be.clone.kakao.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequestDto {
    private Long memberId;
    private String nickname;
    private String profilePic;
    private String content;
    private String createdAt;

}
