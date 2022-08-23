package be.clone.kakao.domain.Room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomMasterResponseDto {
    private Long roomMasterId;
    private String roomName;
    private String recentChat;
    private Long people;
}
