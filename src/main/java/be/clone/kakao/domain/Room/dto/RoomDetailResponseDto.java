package be.clone.kakao.domain.Room.dto;

import be.clone.kakao.domain.Room.RoomMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetaliResponseDto {
    private String roomName;
    private RoomMaster roomMaster;
    private String msg;
}
