package be.clone.kakao.domain.Room;

import be.clone.kakao.domain.Room.dto.RoomDetailRequestDto;
import be.clone.kakao.domain.Room.dto.RoomMasterRequestDto;
import be.clone.kakao.domain.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RoomMaster extends Timestamped {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomName;

    public void update(RoomMasterRequestDto roomMasterRequestDto) {
        this.roomName = roomMasterRequestDto.getRoomName();
    }

}
