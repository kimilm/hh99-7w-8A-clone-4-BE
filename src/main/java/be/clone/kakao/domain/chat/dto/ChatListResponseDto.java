package be.clone.kakao.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatListResponseDto {
    private Long roomMasterId;
    private String recentChat;
    private LocalDateTime createdAt;

    public ChatListResponseDto(Long roomId, ChatDto requestDto) {
        this.roomMasterId = roomId;
        this.recentChat = requestDto.getContent();
        this.createdAt = requestDto.getCreatedAt();
    }
}
