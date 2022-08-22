package be.clone.kakao.domain.roommaster;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoomDTO {

    private Long roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();
    //WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션

    public static ChatRoomDTO create(Long roomId, String name){
        ChatRoomDTO room = new ChatRoomDTO();

        room.roomId = roomId;
        room.name = name;
        return room;
    }
}
