package be.clone.kakao.domain.Room.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomMasterResponseDto {
    private Long roomMasterId;
    private String roomName;
    private String recentChat;
    private Long unReadCount;
    private Long people;
}
