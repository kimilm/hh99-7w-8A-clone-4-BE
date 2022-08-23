package be.clone.kakao.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequestDto {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private Long roomId;
    private String content;
}
