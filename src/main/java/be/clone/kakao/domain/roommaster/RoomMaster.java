package be.clone.kakao.domain.roommaster;

import be.clone.kakao.domain.Timestamped;
import be.clone.kakao.domain.chat.Chat;
import be.clone.kakao.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomMaster extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomMasterId;

    @Column(nullable = false)
    private String roomName;


}

