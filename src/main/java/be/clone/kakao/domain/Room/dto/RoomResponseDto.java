package be.clone.kakao.domain.Room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {
    private Long id;
    private String  room_name;
    private Long people;
}
