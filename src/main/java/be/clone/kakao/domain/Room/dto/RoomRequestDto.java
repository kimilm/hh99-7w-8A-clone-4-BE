package be.clone.kakao.domain.Room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDto {
    private Long id;
    private String  room_name;
    private Long people;
    private String msg;
}
