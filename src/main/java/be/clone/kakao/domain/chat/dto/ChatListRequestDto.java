package be.clone.kakao.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatListRequestDto {
    private Long roomMasterId;
    private String recentChat;
    private String createdAt;

    public ChatListRequestDto(Long roomId, ChatRequestDto requestDto){
        this.roomMasterId = roomId;
        this.recentChat = requestDto.getContent();
        this.createdAt = requestDto.getCreatedAt();
    }
}
