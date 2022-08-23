package be.clone.kakao.domain.Room.dto;

import be.clone.kakao.domain.Room.RoomMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetaliRequestDto {
    private String roomName;
    private RoomMaster roomMaster;
    private String msg;
}
