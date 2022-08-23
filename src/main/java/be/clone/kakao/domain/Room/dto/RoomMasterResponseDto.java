package be.clone.kakao.domain.Room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {
    private String msg;
    private Long roomMasterId;
    private String roomName;
    private Long people;
}
