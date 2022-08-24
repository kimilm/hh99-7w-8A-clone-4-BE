package be.clone.kakao.domain.Room.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoomInviteDto {
    List<Long> friends;
}
